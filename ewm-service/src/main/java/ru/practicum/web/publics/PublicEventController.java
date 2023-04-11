package ru.practicum.web.publics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.practicum.client.StatsClient;
import ru.practicum.dto.EndpointHitDto;
import ru.practicum.dto.event.EventFullDto;
import ru.practicum.mapper.EventMapper;
import ru.practicum.model.Event;
import ru.practicum.service.EventService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/events")
public class PublicEventController {

    private static final String APP_NAME = "ewm-service";

    private final EventService eventService;
    private final EventMapper eventMapper;
    private final StatsClient statsClient;

    @Autowired
    public PublicEventController(EventService eventService,
                                 EventMapper eventMapper,
                                 StatsClient statsClient) {
        this.eventService = eventService;
        this.eventMapper = eventMapper;
        this.statsClient = statsClient;
    }

    @Transactional
    @GetMapping
    public List<EventFullDto> search(@RequestParam(required = false, defaultValue = "") String text,
                                     @RequestParam(required = false) Set<Long> categories,
                                     @RequestParam(required = false) Boolean paid,
                                     @RequestParam(required = false)
                                     @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                     LocalDateTime rangeStart,
                                     @RequestParam(required = false)
                                     @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                     LocalDateTime rangeEnd,
                                     @RequestParam(required = false) Boolean onlyAvailable,
                                     @RequestParam(required = false, defaultValue = "EVENT_DATE") String sort,
                                     @RequestParam(defaultValue = "0") int from,
                                     @RequestParam(defaultValue = "10") int size,
                                     HttpServletRequest request) {
        List<Event> events = eventService.publicSearch(text,
                categories,
                paid,
                rangeStart,
                rangeEnd,
                onlyAvailable,
                sort,
                from,
                size);
        statsClient.addHit(EndpointHitDto.create(APP_NAME, request));
        return events.stream()
                .map(eventMapper::eventMapToFullDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @GetMapping("/{id}")
    public EventFullDto findById(@PathVariable Long id, HttpServletRequest request) {
        Event event = eventService.publicFindById(id);
        statsClient.addHit(EndpointHitDto.create(APP_NAME, request));
        return eventMapper.eventMapToFullDto(event);
    }

}
