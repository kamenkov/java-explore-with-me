package ru.practicum.web.admin;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.comment.CommentDto;
import ru.practicum.dto.comment.UpdateCommentDto;
import ru.practicum.mapper.CommentMapper;
import ru.practicum.model.Comment;
import ru.practicum.service.CommentService;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/admin/comments")
public class AdminCommentController {

    private final CommentMapper commentMapper;
    private final CommentService commentService;

    public AdminCommentController(CommentMapper commentMapper,
                                  CommentService commentService) {
        this.commentMapper = commentMapper;
        this.commentService = commentService;
    }

    @PatchMapping("/{commentId}")
    public CommentDto update(@PathVariable Long commentId,
                             @Valid @RequestBody UpdateCommentDto updateCommentDto) {
        Comment comment = commentMapper.updateCommentDtoMapToComment(updateCommentDto);
        comment = commentService.updateByAdmin(commentId, comment);
        return commentMapper.commentMapToDto(comment);
    }

    @DeleteMapping("/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable Long commentId) {
        commentService.removeByAdmin(commentId);
    }

}
