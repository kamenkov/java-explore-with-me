package ru.practicum.web.privates;

import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.event.ParticipationRequestDto;
import ru.practicum.mapper.RequestMapper;
import ru.practicum.model.Request;
import ru.practicum.service.RequestService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/users/{userId}/requests")
public class PrivateRequestController {

    private final RequestService requestService;
    private final RequestMapper requestMapper;

    public PrivateRequestController(RequestService requestService, RequestMapper requestMapper) {
        this.requestService = requestService;
        this.requestMapper = requestMapper;
    }

    @Transactional(readOnly = true)
    @GetMapping
    public List<ParticipationRequestDto> search(@PathVariable Long userId) {
        return requestService.getRequestsByUserId(userId).stream()
                .map(requestMapper::requestMapToParticipationRequestDto)
                .collect(Collectors.toList());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ParticipationRequestDto create(@PathVariable Long userId,
                                          @RequestParam Long eventId) {
        Request request = requestService.createUserRequest(userId, eventId);
        return requestMapper.requestMapToParticipationRequestDto(request);
    }

    @PatchMapping("/{requestId}/cancel")
    public ParticipationRequestDto cancel(@PathVariable Long userId,
                                          @PathVariable Long requestId) {
        Request request = requestService.cancelUserRequest(userId, requestId);
        return requestMapper.requestMapToParticipationRequestDto(request);
    }
}
