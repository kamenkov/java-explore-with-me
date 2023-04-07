package ru.practicum.dto.event;

public class EventRequestStatusUpdateResult {
    private ParticipationRequestDto confirmedRequests;
    private ParticipationRequestDto rejectedRequests;

    public ParticipationRequestDto getConfirmedRequests() {
        return confirmedRequests;
    }

    public void setConfirmedRequests(ParticipationRequestDto confirmedRequests) {
        this.confirmedRequests = confirmedRequests;
    }

    public ParticipationRequestDto getRejectedRequests() {
        return rejectedRequests;
    }

    public void setRejectedRequests(ParticipationRequestDto rejectedRequests) {
        this.rejectedRequests = rejectedRequests;
    }
}
