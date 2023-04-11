package ru.practicum.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.handler.exception.BadRequestException;
import ru.practicum.handler.exception.ConflictException;
import ru.practicum.mapper.EventMapper;
import ru.practicum.model.Event;
import ru.practicum.model.EventState;
import ru.practicum.model.StateAction;
import ru.practicum.model.User;
import ru.practicum.repository.EventRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static ru.practicum.handler.exception.NotFoundException.notFoundException;

@Service
public class EventService {

    public static final String EVENT_NOT_FOUND = "Event {0} not found";
    private final UserService userService;
    private final EventMapper eventMapper;
    private final EventRepository eventRepository;

    public EventService(UserService userService,
                        EventMapper eventMapper,
                        EventRepository eventRepository) {
        this.userService = userService;
        this.eventMapper = eventMapper;
        this.eventRepository = eventRepository;
    }

    public Event create(Long userId, Event event) {
        User initiator = userService.findById(userId);
        event.setInitiator(initiator);
        return eventRepository.save(event);
    }

    @Transactional
    public Event publicFindById(Long id) {
        Event event = eventRepository.findByStateAndId(EventState.PUBLISHED, id)
                .orElseThrow(notFoundException(EVENT_NOT_FOUND, id));
        event.setViews(event.getViews() + 1);
        return event;
    }

    public Event findById(Long id) {
        return eventRepository.findById(id).orElseThrow(notFoundException(EVENT_NOT_FOUND, id));
    }

    public List<Event> findByIds(List<Long> eventIds) {
        return eventRepository.findAllById(eventIds);
    }

    public List<Event> searchUserEvents(Long userId, int from, int size) {
        Pageable pageable = PageRequest.of(from / size, size);
        User initiator = userService.findById(userId);
        return eventRepository.findEventsByInitiator(initiator, pageable).getContent();
    }

    public Event findUserEvent(Long userId, Long eventId) {
        User initiator = userService.findById(userId);
        return eventRepository.findEventByIdAndInitiator(eventId, initiator)
                .orElseThrow(notFoundException(EVENT_NOT_FOUND, eventId));
    }

    public Event updateEventByUser(Long userId, Long eventId, Event event, StateAction action) {
        Event savedEvent = findUserEvent(userId, eventId);
        if (savedEvent.getState() == EventState.PUBLISHED) {
            throw new ConflictException("Only pending or canceled events can be changed");
        }
        if (action != null) {
            switch (action) {
                case CANCEL_REVIEW:
                    savedEvent.setState(EventState.CANCELED);
                    break;
                case SEND_TO_REVIEW:
                    savedEvent.setState(EventState.PENDING);
                    break;
                default:
                    throw new BadRequestException("Wrong state action {0}", action);
            }
        }
        eventMapper.updateEvent(event, savedEvent);
        return eventRepository.save(savedEvent);
    }

    public List<Event> search(Set<Long> users,
                              Set<EventState> states,
                              Set<Long> categories,
                              LocalDateTime rangeStart,
                              LocalDateTime rangeEnd,
                              int from,
                              int size) {
        Pageable pageable = PageRequest.of(from / size, size);
        if (rangeStart == null) {
            rangeStart = LocalDateTime.now();
        }
        if (rangeEnd == null) {
            rangeEnd = LocalDateTime.now().plusYears(1000L);
        }
        return eventRepository.adminEventSearch(users,
                states,
                categories,
                rangeStart,
                rangeEnd,
                pageable).getContent();
    }

    public Event updateEventByAdmin(Long eventId, Event event, StateAction action) {
        Event savedEvent = eventRepository.findById(eventId)
                .orElseThrow(notFoundException(EVENT_NOT_FOUND, eventId));
        if (savedEvent.getState() != EventState.PENDING) {
            throw new ConflictException("Event not in the Pending state. Current state: {0}", savedEvent.getState());
        }
        if (action != null) {
            switch (action) {
                case REJECT_EVENT:
                    savedEvent.setState(EventState.CANCELED);
                    break;
                case PUBLISH_EVENT:
                    savedEvent.setState(EventState.PUBLISHED);
                    savedEvent.setPublishedOn(LocalDateTime.now());
                    break;
                default:
                    throw new BadRequestException("Wrong state action {0}", action);
            }
        }
        eventMapper.updateEvent(event, savedEvent);
        return eventRepository.save(savedEvent);
    }

    @Transactional
    public List<Event> publicSearch(String text,
                                    Set<Long> categories,
                                    Boolean paid,
                                    LocalDateTime rangeStart,
                                    LocalDateTime rangeEnd,
                                    Boolean onlyAvailable,
                                    String sort,
                                    int from,
                                    int size) {
        Sort.TypedSort<Event> eventSort = Sort.sort(Event.class);
        Sort sorting;
        switch (sort) {
            case "EVENT_DATE":
                sorting = eventSort.by(Event::getEventDate).descending();
                break;
            case "VIEWS":
                sorting = eventSort.by(Event::getViews).descending();
                break;
            default:
                throw new BadRequestException("Unavailable sorting option");
        }
        Pageable pageable = PageRequest.of(from / size, size, sorting);
        if (rangeStart == null) {
            rangeStart = LocalDateTime.now();
        }
        if (rangeEnd == null) {
            rangeEnd = LocalDateTime.now().plusYears(1000L);
        }
        List<Event> events = eventRepository.publicEventSearch(text,
                categories,
                paid,
                rangeStart,
                rangeEnd,
                onlyAvailable,
                EventState.PUBLISHED,
                pageable).getContent();

        for (Event event : events) {
            event.setViews(event.getViews() + 1);
        }

        return events;
    }
}
