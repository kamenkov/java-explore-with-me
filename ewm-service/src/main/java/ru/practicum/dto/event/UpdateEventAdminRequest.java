package ru.practicum.dto.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import ru.practicum.constraint.FutureTime;

import java.time.LocalDateTime;

public class UpdateEventAdminRequest extends UpdateEventRequest {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @FutureTime
    private LocalDateTime eventDate;

    private StateAction stateAction;

    public LocalDateTime getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDateTime eventDate) {
        this.eventDate = eventDate;
    }

    public StateAction getStateAction() {
        return stateAction;
    }

    public void setStateAction(StateAction stateAction) {
        this.stateAction = stateAction;
    }

    public enum StateAction {
        PUBLISH_EVENT, REJECT_EVENT
    }

}
