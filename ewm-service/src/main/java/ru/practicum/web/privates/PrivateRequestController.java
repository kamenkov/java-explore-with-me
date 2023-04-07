package ru.practicum.web.privates;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.event.ParticipationRequestDto;

import java.util.List;

@RestController
@RequestMapping(path = "/users/{userId}/requests")
public class PrivateRequestController {

    @Transactional(readOnly = true)
    @GetMapping
    public List<ParticipationRequestDto> search(@PathVariable Long userId) {
        return null;
    }

    @PostMapping("/{requestId}")
    public ParticipationRequestDto create(@PathVariable Long userId,
                                          @PathVariable Long requestId) {
        return null;
    }

    @PatchMapping("/{requestId}")
    public ParticipationRequestDto cancel(@PathVariable Long userId,
                                          @PathVariable Long requestId) {
        return null;
    }
}
