package ru.practicum.web.publics;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.event.EventFullDto;
import ru.practicum.mapper.EventMapper;
import ru.practicum.model.Event;
import ru.practicum.service.EventService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/events")
public class PublicEventController {

    private final EventService eventService;
    private final EventMapper eventMapper;

    public PublicEventController(EventService eventService,
                                 EventMapper eventMapper) {
        this.eventService = eventService;
        this.eventMapper = eventMapper;
    }

    @Transactional(readOnly = true)
    @GetMapping
    public List<EventFullDto> search(@RequestParam(required = false) String text,
                                     @RequestParam(required = false) Set<Long> categories,
                                     @RequestParam(required = false) boolean paid,
                                     @RequestParam(required = false)
                                     @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                     LocalDateTime rangeStart,
                                     @RequestParam(required = false)
                                     @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                     LocalDateTime rangeEnd,
                                     @RequestParam(required = false) boolean onlyAvailable,
                                     @RequestParam(required = false) String sort,
                                     @RequestParam(defaultValue = "0") int from,
                                     @RequestParam(defaultValue = "10") int size) {
        List<Event> events = eventService.publicSearch(text,
                categories,
                paid,
                rangeStart,
                rangeEnd,
                onlyAvailable,
                sort,
                from,
                size);
        return events.stream()
                .map(eventMapper::eventMapToFullDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @GetMapping("/{id}")
    public EventFullDto findById(@PathVariable Long id) {
        Event event = eventService.publicFindById(id);
        return eventMapper.eventMapToFullDto(event);
    }

}
