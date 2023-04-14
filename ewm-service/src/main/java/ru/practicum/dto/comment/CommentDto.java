package ru.practicum.dto.comment;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class CommentDto {

    private Long id;

    private Long author;

    private Long event;

    private String commentBody;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime created;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAuthor() {
        return author;
    }

    public void setAuthor(Long author) {
        this.author = author;
    }

    public Long getEvent() {
        return event;
    }

    public void setEvent(Long event) {
        this.event = event;
    }

    public String getCommentBody() {
        return commentBody;
    }

    public void setCommentBody(String commentBody) {
        this.commentBody = commentBody;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }
}
