package ru.practicum.service;

import org.springframework.stereotype.Service;
import ru.practicum.dto.event.EventRequestStatusUpdateRequest;
import ru.practicum.dto.event.EventRequestStatusUpdateResult;
import ru.practicum.dto.event.ParticipationRequestDto;
import ru.practicum.handler.exception.BadRequestException;
import ru.practicum.handler.exception.ConflictException;
import ru.practicum.handler.exception.NotFoundException;
import ru.practicum.mapper.RequestMapper;
import ru.practicum.model.*;
import ru.practicum.repository.RequestRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class RequestService {

    private final RequestRepository requestRepository;
    private final RequestMapper requestMapper;
    private final EventService eventService;
    private final UserService userService;

    public RequestService(RequestRepository requestRepository,
                          RequestMapper requestMapper, EventService eventService, UserService userService) {
        this.requestRepository = requestRepository;
        this.requestMapper = requestMapper;
        this.eventService = eventService;
        this.userService = userService;
    }

    public List<Request> getRequestsByEvent(Event event) {
        return requestRepository.findByEvent(event);
    }

    public EventRequestStatusUpdateResult updateRequestsStatus(Event event,
                                                               EventRequestStatusUpdateRequest updateRequest) {
        int participationLimit = event.getParticipantLimit();

        if (participationLimit == 0 || Boolean.FALSE.equals(event.isRequestModeration())) {
            return new EventRequestStatusUpdateResult(Collections.emptyList(), Collections.emptyList());
        }

        if (participationLimit == event.getConfirmedRequests()) {
            throw new ConflictException("The participant limit has been reached");
        }

        List<ParticipationRequestDto> rejectedRequests = new ArrayList<>();
        List<ParticipationRequestDto> confirmedRequests = new ArrayList<>();
        List<Request> requests = requestRepository.findRequestsByIdIn(updateRequest.getRequestIds());
        RequestStatus newStatus = updateRequest.getStatus();

        for (Request request : requests) {
            if (request.getStatus() != RequestStatus.PENDING) {
                throw new BadRequestException("Request must have status PENDING");
            }
            if (newStatus == RequestStatus.CONFIRMED) {
                if (participationLimit <= 0) {
                    request.setStatus(RequestStatus.REJECTED);
                    rejectedRequests.add(requestMapper.requestMapToParticipationRequestDto(request));
                } else {
                    request.setStatus(newStatus);
                    participationLimit--;
                    confirmedRequests.add(requestMapper.requestMapToParticipationRequestDto(request));
                }
            } else if (newStatus == RequestStatus.REJECTED) {
                request.setStatus(RequestStatus.REJECTED);
                rejectedRequests.add(requestMapper.requestMapToParticipationRequestDto(request));
            }
        }
        requestRepository.saveAll(requests);
        return new EventRequestStatusUpdateResult(confirmedRequests, rejectedRequests);
    }

    public List<Request> getRequestsByUserId(Long id) {
        return requestRepository.findByRequesterId(id);
    }

    public Optional<Request> findRequestByUserIdAndEventId(Long userId, Long eventId) {
        return requestRepository.findByRequesterIdAndEventId(userId, eventId);
    }

    public Request createUserRequest(Long userId, Long eventId) {
        Optional<Request> existedRequest = findRequestByUserIdAndEventId(userId, eventId);
        if (existedRequest.isPresent()) {
            throw new ConflictException("Request already exists. ID {0}", existedRequest.get().getId());
        }
        Event event = eventService.findById(eventId);
        if (event.getState() != EventState.PUBLISHED) {
            throw new ConflictException("Can't create request for non-published event");
        }
        User requester = userService.findById(userId);
        if (event.getInitiator().equals(requester)) {
            throw new ConflictException("Can't create request for your own event");
        }
        int participantLimit = event.getParticipantLimit();
        if (participantLimit != 0 && participantLimit == event.getConfirmedRequests()) {
            throw new ConflictException("Limit reached");
        }
        Request request = new Request();
        request.setRequester(requester);
        request.setEvent(event);
        if (Boolean.FALSE.equals(event.isRequestModeration())) {
            request.setStatus(RequestStatus.CONFIRMED);
        } else {
            request.setStatus(RequestStatus.PENDING);
        }
        return requestRepository.save(request);
    }


    public Request cancelUserRequest(Long userId, Long requestId) {
        Optional<Request> optionalRequest = requestRepository.findByRequesterIdAndId(userId, requestId);
        if (optionalRequest.isEmpty()) {
            throw new NotFoundException("Request {0} not found", requestId);
        }
        Request request = optionalRequest.get();
        request.setStatus(RequestStatus.CANCELED);
        return requestRepository.save(request);
    }
}
