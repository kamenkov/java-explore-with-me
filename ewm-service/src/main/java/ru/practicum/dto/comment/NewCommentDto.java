package ru.practicum.dto.comment;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class NewCommentDto {

    @NotNull
    private Long event;

    @NotEmpty
    @Size(max = 2000)
    private String commentBody;

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

}
