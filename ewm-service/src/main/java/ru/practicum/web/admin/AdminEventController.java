package ru.practicum.web.admin;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.event.EventFullDto;
import ru.practicum.dto.event.UpdateEventAdminRequest;
import ru.practicum.mapper.EventMapper;
import ru.practicum.model.Event;
import ru.practicum.model.EventState;
import ru.practicum.model.StateAction;
import ru.practicum.service.EventService;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/admin/events")
public class AdminEventController {

    private final EventService eventService;
    private final EventMapper eventMapper;

    public AdminEventController(EventService eventService, EventMapper eventMapper) {
        this.eventService = eventService;
        this.eventMapper = eventMapper;
    }

    @Transactional(readOnly = true)
    @GetMapping
    public List<EventFullDto> search(@RequestParam(required = false) Set<Long> users,
                                     @RequestParam(required = false) Set<EventState> states,
                                     @RequestParam(required = false) Set<Long> categories,
                                     @RequestParam(required = false)
                                     @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                     LocalDateTime rangeStart,
                                     @RequestParam(required = false)
                                     @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                     LocalDateTime rangeEnd,
                                     @RequestParam(defaultValue = "0") int from,
                                     @RequestParam(defaultValue = "10") int size) {
        return eventService.search(users, states, categories, rangeStart, rangeEnd, from, size).stream()
                .map(eventMapper::eventMapToFullDto)
                .collect(Collectors.toList());
    }

    @PatchMapping("/{id}")
    public EventFullDto update(@PathVariable Long id,
                               @Valid @RequestBody UpdateEventAdminRequest updateEventAdminRequest) {
        Event event = eventMapper.updateEventAdminRequestMapToEvent(updateEventAdminRequest);
        StateAction action = updateEventAdminRequest.getStateAction();
        event = eventService.updateEventByAdmin(id, event, action);
        return eventMapper.eventMapToFullDto(event);
    }
}
