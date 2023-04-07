package ru.practicum.web.publics;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.event.EventFullDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(path = "/events")
public class PublicEventController {

    @Transactional(readOnly = true)
    @GetMapping
    public List<EventFullDto> search(@RequestParam(required = false) String text,
                                        @RequestParam(required = false) Set<Long> categories,
                                        @RequestParam(required = false) boolean paid,
                                        @RequestParam(required = false) LocalDateTime rangeStart,
                                        @RequestParam(required = false) LocalDateTime rangeEnd,
                                        @RequestParam(required = false) boolean onlyAvailable,
                                        @RequestParam(required = false) String sort,
                                        @RequestParam(defaultValue = "0") int from,
                                        @RequestParam(defaultValue = "10") int size) {
        return null;
    }

    @Transactional(readOnly = true)
    @GetMapping("/{id}")
    public EventFullDto findById(@PathVariable Long id) {
        return null;
    }

}
