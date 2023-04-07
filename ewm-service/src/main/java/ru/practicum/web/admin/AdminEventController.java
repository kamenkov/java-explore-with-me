package ru.practicum.web.admin;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.event.EventFullDto;
import ru.practicum.dto.event.UpdateEventAdminRequest;
import ru.practicum.model.EventState;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(path = "/admin/events")
public class AdminEventController {

    @Transactional(readOnly = true)
    @GetMapping
    public List<EventFullDto> search(@RequestParam(required = false) Set<Long> users,
                                        @RequestParam(required = false) Set<EventState> states,
                                        @RequestParam(required = false) Set<Long> categories,
                                        @RequestParam(required = false) LocalDateTime rangeStart,
                                        @RequestParam(required = false) LocalDateTime rangeEnd,
                                        @RequestParam(defaultValue = "0") int from,
                                        @RequestParam(defaultValue = "10") int size) {
        return null;
    }

    @PatchMapping("/{id}")
    public EventFullDto update(@PathVariable Long id,
                               @RequestBody UpdateEventAdminRequest updateEventAdminRequest) {
        return null;
    }
}
