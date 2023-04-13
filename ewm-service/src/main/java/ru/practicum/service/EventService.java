package ru.practicum.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.client.StatsClient;
import ru.practicum.dto.event.UpdateEventAdminRequest;
import ru.practicum.dto.event.UpdateEventUserRequest;
import ru.practicum.handler.exception.BadRequestException;
import ru.practicum.handler.exception.ConflictException;
import ru.practicum.mapper.EventMapper;
import ru.practicum.model.Category;
import ru.practicum.model.Event;
import ru.practicum.model.EventState;
import ru.practicum.model.User;
import ru.practicum.repository.EventRepository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static ru.practicum.handler.exception.NotFoundException.notFoundException;

@Service
public class EventService {

    public static final String EVENT_NOT_FOUND = "Event {0} not found";
    private final UserService userService;
    private final CategoryService categoryService;
    private final EventMapper eventMapper;
    private final EventRepository eventRepository;
    private final StatsClient statsClient;

    @Autowired
    public EventService(UserService userService,
                        CategoryService categoryService,
                        EventMapper eventMapper,
                        EventRepository eventRepository,
                        StatsClient statsClient) {
        this.userService = userService;
        this.categoryService = categoryService;
        this.eventMapper = eventMapper;
        this.eventRepository = eventRepository;
        this.statsClient = statsClient;
    }

    public Event create(Long userId, Event event, Long categoryId) {
        User initiator = userService.findById(userId);
        Category category = categoryService.findById(categoryId);
        event.setInitiator(initiator);
        event.setCategory(category);
        return eventRepository.save(event);
    }

    @Transactional(readOnly = true)
    public Event publicFindById(Long id) {
        Event event = eventRepository.findByStateAndId(EventState.PUBLISHED, id)
                .orElseThrow(notFoundException(EVENT_NOT_FOUND, id));
        Long views = statsClient.getViewsForEvents(List.of(id), event.getPublishedOn()).get(id);
        if (views != null) {
            event.setViews(views);
        }
        return event;
    }

    public Event findById(@Nullable Long id) {
        if (id == null) {
            return null;
        }
        return eventRepository.findById(id).orElseThrow(notFoundException(EVENT_NOT_FOUND, id));
    }

    public List<Event> findByIds(List<Long> eventIds) {
        if (eventIds == null || eventIds.contains(null)) {
            return Collections.emptyList();
        }
        return eventRepository.findAllById(eventIds);
    }

    @Transactional(readOnly = true)
    public List<Event> searchUserEvents(Long userId, int from, int size) {
        Pageable pageable = PageRequest.of(from / size, size);
        User initiator = userService.findById(userId);
        List<Event> events = eventRepository.findEventsByInitiator(initiator, pageable).getContent();
        Map<Long, Long> views = getViewsByEvents(events);
        if (views.isEmpty()) {
            return events;
        }
        for (Event event : events) {
            Long hits = views.get(event.getId());
            if (hits != null) {
                event.setViews(hits);
            }
        }
        return events;
    }

    public Event findUserEvent(Long userId, Long eventId) {
        User initiator = userService.findById(userId);
        Event event = eventRepository.findEventByIdAndInitiator(eventId, initiator)
                .orElseThrow(notFoundException(EVENT_NOT_FOUND, eventId));
        if (event.getPublishedOn() == null) {
            return event;
        }
        Long views = statsClient.getViewsForEvents(List.of(eventId), event.getPublishedOn()).get(eventId);
        if (views != null) {
            event.setViews(views);
        }
        return event;
    }

    public Event updateEventByUser(Long userId, Long eventId, Event event,
                                   UpdateEventUserRequest.StateAction action, Long categoryId) {
        Event savedEvent = findUserEvent(userId, eventId);
        if (savedEvent.getState() == EventState.PUBLISHED) {
            throw new ConflictException("Only pending or canceled events can be changed");
        }
        Category category = categoryService.findById(categoryId);
        if (category != null) {
            savedEvent.setCategory(category);
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

    @Transactional(readOnly = true)
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
        List<Event> events = eventRepository.adminEventSearch(users,
                states,
                categories,
                rangeStart,
                rangeEnd,
                pageable).getContent();

        Map<Long, Long> views = getViewsByEvents(events);
        if (views.isEmpty()) {
            return events;
        }
        for (Event event : events) {
            Long hits = views.get(event.getId());
            if (hits != null) {
                event.setViews(hits);
            }
        }
        return events;
    }

    public Event updateEventByAdmin(Long eventId, Event event,
                                    UpdateEventAdminRequest.StateAction action, Long categoryId) {
        Event savedEvent = eventRepository.findById(eventId)
                .orElseThrow(notFoundException(EVENT_NOT_FOUND, eventId));
        if (savedEvent.getState() != EventState.PENDING) {
            throw new ConflictException("Event not in the Pending state. Current state: {0}", savedEvent.getState());
        }
        Category category = categoryService.findById(categoryId);
        if (category != null) {
            savedEvent.setCategory(category);
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

    @Transactional(readOnly = true)
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
        Pageable pageable;
        switch (sort) {
            case "EVENT_DATE":
                sorting = eventSort.by(Event::getEventDate).descending();
                pageable = PageRequest.of(from / size, size, sorting);
                break;
            case "VIEWS":
                pageable = Pageable.unpaged();
                break;
            default:
                throw new BadRequestException("Unavailable sorting option");
        }
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

        Map<Long, Long> views = getViewsByEvents(events);
        for (Event event : events) {
            Long hits = views.get(event.getId());
            if (hits != null) {
                event.setViews(hits);
            }
        }
        if ("VIEWS".equals(sort)) {
            return events.stream()
                    .sorted(Comparator.comparingLong(Event::getViews).reversed())
                    .skip(from)
                    .limit(size)
                    .collect(Collectors.toList());
        }
        return events;
    }

    private Map<Long, Long> getViewsByEvents(List<Event> events) {

        LocalDateTime startDate = events.stream()
                .map(Event::getPublishedOn)
                .filter(Objects::nonNull)
                .min(LocalDateTime::compareTo)
                .orElse(null);
        List<Long> eventIds = events.stream()
                .map(Event::getId)
                .collect(Collectors.toList());

        return statsClient.getViewsForEvents(eventIds, startDate);
    }
}
