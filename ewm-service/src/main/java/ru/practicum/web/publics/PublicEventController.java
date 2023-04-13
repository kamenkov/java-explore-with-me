package ru.practicum.web.publics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.client.StatsClient;
import ru.practicum.dto.EndpointHitDto;
import ru.practicum.dto.event.EventFullDto;
import ru.practicum.mapper.EventMapper;
import ru.practicum.model.Event;
import ru.practicum.service.EventService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Validated
@RestController
@RequestMapping(path = "/events")
public class PublicEventController {

    @Value("${app.name}")
    private String appName;
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

    @GetMapping
    public List<EventFullDto> search(@RequestParam(required = false, defaultValue = "") String text,
                                     @RequestParam(required = false) Set<Long> categories,
                                     @RequestParam(required = false) Boolean paid,
                                     @RequestParam(required = false) LocalDateTime rangeStart,
                                     @RequestParam(required = false) LocalDateTime rangeEnd,
                                     @RequestParam(defaultValue = "false") Boolean onlyAvailable,
                                     @RequestParam(defaultValue = "EVENT_DATE") String sort,
                                     @RequestParam(defaultValue = "0") @PositiveOrZero int from,
                                     @RequestParam(defaultValue = "10") @Positive int size,
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
        statsClient.addHit(EndpointHitDto.create(appName, request));
        return events.stream()
                .map(eventMapper::eventMapToFullDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public EventFullDto findById(@PathVariable Long id, HttpServletRequest request) {
        Event event = eventService.publicFindById(id);
        statsClient.addHit(EndpointHitDto.create(appName, request));
        return eventMapper.eventMapToFullDto(event);
    }

}
