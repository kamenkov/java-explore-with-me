package ru.practicum.web.privates;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.comment.CommentDto;
import ru.practicum.dto.comment.NewCommentDto;
import ru.practicum.dto.comment.UpdateCommentDto;
import ru.practicum.mapper.CommentMapper;
import ru.practicum.model.Comment;
import ru.practicum.service.CommentService;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/users/{userId}/comments")
public class PrivateCommentController {

    private final CommentMapper commentMapper;
    private final CommentService commentService;

    public PrivateCommentController(CommentMapper commentMapper,
                                    CommentService commentService) {
        this.commentMapper = commentMapper;
        this.commentService = commentService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDto create(@PathVariable Long userId,
                             @Valid @RequestBody NewCommentDto newCommentDto) {
        Comment comment = commentMapper.newCommentDtoMapToComment(newCommentDto);
        comment = commentService.create(userId, comment, newCommentDto.getEvent());
        return commentMapper.commentMapToDto(comment);
    }

    @PatchMapping("/{commentId}")
    public CommentDto update(@PathVariable Long userId,
                             @PathVariable Long commentId,
                             @Valid @RequestBody UpdateCommentDto updateCommentDto) {
        Comment comment = commentMapper.updateCommentDtoMapToComment(updateCommentDto);
        comment = commentService.update(userId, commentId, comment);
        return commentMapper.commentMapToDto(comment);
    }

    @DeleteMapping("/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable Long userId,
                       @PathVariable Long commentId) {
        commentService.remove(userId, commentId);
    }
}
