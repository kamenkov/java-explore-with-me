package ru.practicum.dto.comment;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class UpdateCommentDto {

    @NotEmpty
    @Size(max = 2000)
    private String commentBody;

    public String getCommentBody() {
        return commentBody;
    }

    public void setCommentBody(String commentBody) {
        this.commentBody = commentBody;
    }

}
