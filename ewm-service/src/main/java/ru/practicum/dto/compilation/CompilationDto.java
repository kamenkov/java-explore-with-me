package ru.practicum.dto.compilation;

import ru.practicum.dto.event.EventShortDto;

import java.util.List;

public class CompilationDto {

    private List<EventShortDto> events;

    private Long id;

    private Boolean pinned;

    private String title;

    public List<EventShortDto> getEvents() {
        return events;
    }

    public void setEvents(List<EventShortDto> events) {
        this.events = events;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
