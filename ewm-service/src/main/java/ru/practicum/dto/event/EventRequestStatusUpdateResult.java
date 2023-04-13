package ru.practicum.dto.event;

import java.util.List;

public class EventRequestStatusUpdateResult {
    private List<ParticipationRequestDto> confirmedRequests;
    private List<ParticipationRequestDto> rejectedRequests;

    public EventRequestStatusUpdateResult(List<ParticipationRequestDto> confirmedRequests,
                                          List<ParticipationRequestDto> rejectedRequests) {
        this.confirmedRequests = confirmedRequests;
        this.rejectedRequests = rejectedRequests;
    }

    public List<ParticipationRequestDto> getConfirmedRequests() {
        return confirmedRequests;
    }

    public void setConfirmedRequests(List<ParticipationRequestDto> confirmedRequests) {
        this.confirmedRequests = confirmedRequests;
    }

    public List<ParticipationRequestDto> getRejectedRequests() {
        return rejectedRequests;
    }

    public void setRejectedRequests(List<ParticipationRequestDto> rejectedRequests) {
        this.rejectedRequests = rejectedRequests;
    }
}
