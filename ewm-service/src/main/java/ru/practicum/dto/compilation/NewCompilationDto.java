package ru.practicum.dto.compilation;

import javax.validation.constraints.NotBlank;
import java.util.List;

public class NewCompilationDto {

    private List<Long> events;

    private Boolean pinned;

    @NotBlank
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
