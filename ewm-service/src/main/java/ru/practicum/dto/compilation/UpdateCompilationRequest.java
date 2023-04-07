package ru.practicum.dto.compilation;

import ru.practicum.dto.event.EventShortDto;

import java.util.List;

public class UpdateCompilationRequest {

    private List<EventShortDto> events;

    private boolean pinned;

    private String title;

    public List<EventShortDto> getEvents() {
        return events;
    }

    public void setEvents(List<EventShortDto> events) {
        this.events = events;
    }

    public boolean isPinned() {
        return pinned;
    }

    public void setPinned(boolean pinned) {
        this.pinned = pinned;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
