package ru.practicum.web.privates;

import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.event.*;
import ru.practicum.mapper.EventMapper;
import ru.practicum.mapper.RequestMapper;
import ru.practicum.model.Event;
import ru.practicum.model.Request;
import ru.practicum.service.EventService;
import ru.practicum.service.RequestService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/users/{userId}/events")
public class PrivateEventController {

    private final EventMapper eventMapper;
    private final RequestMapper requestMapper;
    private final EventService eventService;
    private final RequestService requestService;

    public PrivateEventController(EventMapper eventMapper,
                                  RequestMapper requestMapper,
                                  EventService eventService,
                                  RequestService requestService) {
        this.eventMapper = eventMapper;
        this.requestMapper = requestMapper;
        this.eventService = eventService;
        this.requestService = requestService;
    }

    @Transactional(readOnly = true)
    @GetMapping
    public List<EventShortDto> search(@PathVariable Long userId,
                                      @RequestParam(defaultValue = "0") int from,
                                      @RequestParam(defaultValue = "10") int size) {
        List<Event> userEvents = eventService.searchUserEvents(userId, from, size);
        return userEvents.stream()
                .map(eventMapper::eventMapToShortDto)
                .collect(Collectors.toList());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventFullDto create(@PathVariable Long userId,
                               @Valid @RequestBody NewEventDto newEventDto) {
        Event event = eventMapper.newEventDtoMapToEvent(newEventDto);
        event = eventService.create(userId, event);
        return eventMapper.eventMapToFullDto(event);
    }

    @Transactional(readOnly = true)
    @GetMapping("/{eventId}")
    public EventFullDto getEvent(@PathVariable Long userId,
                                 @PathVariable Long eventId) {
        Event event = eventService.findUserEvent(userId, eventId);
        return eventMapper.eventMapToFullDto(event);
    }

    @PatchMapping("/{eventId}")
    public EventFullDto updateEvent(@PathVariable Long userId,
                                    @PathVariable Long eventId,
                                    @Valid @RequestBody UpdateEventUserRequest updateEventUserRequest) {
        Event event = eventMapper.updateEventUserRequestMapToEvent(updateEventUserRequest);
        event = eventService.updateEventByUser(userId, eventId, event, updateEventUserRequest.getStateAction());
        return eventMapper.eventMapToFullDto(event);
    }

    @Transactional(readOnly = true)
    @GetMapping("/{eventId}/requests")
    public List<ParticipationRequestDto> getRequests(@PathVariable Long userId,
                                                     @PathVariable Long eventId) {
        Event event = eventService.findUserEvent(userId, eventId);
        List<Request> requests = requestService.getRequestsByEvent(event);
        return requests.stream()
                .map(requestMapper::requestMapToParticipationRequestDto)
                .collect(Collectors.toList());
    }

    @PatchMapping("/{eventId}/requests")
    public EventRequestStatusUpdateResult updateRequests(@PathVariable Long userId,
                                                         @PathVariable Long eventId,
                                                         @RequestBody EventRequestStatusUpdateRequest request) {
        Event event = eventService.findUserEvent(userId, eventId);
        return requestService.updateRequestsStatus(event, request);
    }

}
