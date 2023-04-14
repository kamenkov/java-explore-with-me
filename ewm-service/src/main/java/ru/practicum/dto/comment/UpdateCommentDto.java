package ru.practicum.dto.comment;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UpdateCommentDto {

    @NotBlank
    @Size(max = 2000)
    private String commentBody;

    public String getCommentBody() {
        return commentBody;
    }

    public void setCommentBody(String commentBody) {
        this.commentBody = commentBody;
    }

}
