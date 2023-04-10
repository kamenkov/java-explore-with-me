package ru.practicum.dto.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import ru.practicum.constraint.FutureTime;
import ru.practicum.model.Location;
import ru.practicum.model.StateAction;

import java.time.LocalDateTime;

public class UpdateEventUserRequest {

    private String annotation;

    private Long category;

    @FutureTime(hours = 2)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;

    private String title;

    private String description;

    private Location location;

    private Boolean paid;

    private Boolean requestModeration;

    private StateAction stateAction;

    private Integer participantLimit;

    public String getAnnotation() {
        return annotation;
    }

    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }

    public Long getCategory() {
        return category;
    }

    public void setCategory(Long category) {
        this.category = category;
    }

    public LocalDateTime getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDateTime eventDate) {
        this.eventDate = eventDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Boolean isPaid() {
        return paid;
    }

    public void setPaid(Boolean paid) {
        this.paid = paid;
    }

    public Boolean isRequestModeration() {
        return requestModeration;
    }

    public void setRequestModeration(Boolean requestModeration) {
        this.requestModeration = requestModeration;
    }

    public StateAction getStateAction() {
        return stateAction;
    }

    public void setStateAction(StateAction stateAction) {
        this.stateAction = stateAction;
    }

    public Integer getParticipantLimit() {
        return participantLimit;
    }

    public void setParticipantLimit(Integer participantLimit) {
        this.participantLimit = participantLimit;
    }
}
