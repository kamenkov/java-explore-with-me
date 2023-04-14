package ru.practicum.web.publics;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.comment.CommentDto;
import ru.practicum.mapper.CommentMapper;
import ru.practicum.model.Comment;
import ru.practicum.service.CommentService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;
import java.util.stream.Collectors;

@Validated
@RestController
@RequestMapping(path = "/comments")
public class PublicCommentController {

    private final CommentMapper commentMapper;
    private final CommentService commentService;

    public PublicCommentController(CommentMapper commentMapper,
                                   CommentService commentService) {
        this.commentMapper = commentMapper;
        this.commentService = commentService;
    }

    @GetMapping
    List<CommentDto> getComments(@RequestParam Long eventId,
                                 @RequestParam(defaultValue = "0") @PositiveOrZero int from,
                                 @RequestParam(defaultValue = "10") @Positive int size) {
        return commentService.getCommentsByEvent(eventId, from, size).stream()
                .map(commentMapper::commentMapToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    CommentDto getComment(@PathVariable Long id) {
        Comment comment = commentService.findById(id);
        return commentMapper.commentMapToDto(comment);
    }

}
