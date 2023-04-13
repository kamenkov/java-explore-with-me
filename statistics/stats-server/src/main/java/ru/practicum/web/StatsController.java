package ru.practicum.web;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.EndpointHitDto;
import ru.practicum.dto.ViewStatsDto;
import ru.practicum.service.StatsService;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.util.List;


@Validated
@RestController
public class StatsController {

    private final StatsService statsService;

    public StatsController(StatsService statsService) {
        this.statsService = statsService;
    }

    @PostMapping("/hit")
    @ResponseStatus(HttpStatus.CREATED)
    EndpointHitDto saveHit(@Valid @RequestBody EndpointHitDto endpointHitDto) {
        return statsService.create(endpointHitDto);
    }

    @GetMapping("/stats")
    List<ViewStatsDto> getHits(@RequestParam Timestamp start,
                               @RequestParam Timestamp end,
                               @RequestParam(required = false) List<String> uris,
                               @RequestParam(required = false, defaultValue = "false") boolean unique) {
        return statsService.getHits(start, end, uris, unique);
    }
}
