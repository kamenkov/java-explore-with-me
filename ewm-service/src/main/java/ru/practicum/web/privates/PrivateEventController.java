package ru.practicum.web.privates;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.event.EventFullDto;
import ru.practicum.dto.event.EventShortDto;
import ru.practicum.dto.event.NewEventDto;
import ru.practicum.dto.event.UpdateEventUserRequest;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/users/{userId}/events")
public class PrivateEventController {

    @Transactional(readOnly = true)
    @GetMapping
    public List<EventShortDto> search(@PathVariable Long userId,
                                      @RequestParam(defaultValue = "0") int from,
                                      @RequestParam(defaultValue = "10") int size) {
        return null;
    }

    @PostMapping
    public EventFullDto create(@PathVariable Long userId,
                               @Valid @RequestBody NewEventDto newEventDto) {
        return null;
    }

    @Transactional(readOnly = true)
    @GetMapping("/{eventId}")
    public List<EventShortDto> getEvents(@PathVariable Long userId,
                                        @PathVariable Long eventId) {
        return null;
    }

    @PatchMapping("/{eventId}")
    public List<EventShortDto> updateEvent(@PathVariable Long userId,
                                           @PathVariable Long eventId,
                                           @RequestBody UpdateEventUserRequest updateEventUserRequest) {
        return null;
    }

    @Transactional(readOnly = true)
    @GetMapping("/{eventId}/requests")
    public List<EventShortDto> getRequests(@PathVariable Long userId,
                                           @PathVariable Long eventId) {
        return null;
    }

    @PatchMapping("/{eventId}/requests")
    public List<EventShortDto> updateRequests(@PathVariable Long userId,
                                              @PathVariable Long eventId,
                                              @RequestBody UpdateEventUserRequest updateEventUserRequest) {
        return null;
    }

}
