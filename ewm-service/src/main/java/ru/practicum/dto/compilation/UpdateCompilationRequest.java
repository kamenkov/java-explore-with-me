package ru.practicum.dto.compilation;

import java.util.List;

public class UpdateCompilationRequest {

    private List<Long> events;

    private Boolean pinned;

    private String title;

    public List<Long> getEvents() {
        return events;
    }

    public void setEvents(List<Long> events) {
        this.events = events;
    }

    public Boolean isPinned() {
        return pinned;
    }

    public void setPinned(Boolean pinned) {
        this.pinned = pinned;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
