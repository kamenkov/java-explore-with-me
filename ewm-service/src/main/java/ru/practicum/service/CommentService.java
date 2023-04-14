package ru.practicum.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.handler.exception.ConflictException;
import ru.practicum.model.Comment;
import ru.practicum.model.Event;
import ru.practicum.model.EventState;
import ru.practicum.model.User;
import ru.practicum.repository.CommentRepository;

import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.handler.exception.NotFoundException.notFoundException;

@Service
public class CommentService {

    public static final int MINUTES_FOR_EDITING = 30;
    private final CommentRepository commentRepository;
    private final EventService eventService;
    private final UserService userService;

    public CommentService(CommentRepository commentRepository,
                          EventService eventService,
                          UserService userService) {
        this.commentRepository = commentRepository;
        this.eventService = eventService;
        this.userService = userService;
    }

    @Transactional(readOnly = true)
    public List<Comment> getCommentsByEvent(Long eventId, int from, int size) {
        Sort.TypedSort<Comment> eventSort = Sort.sort(Comment.class);
        Sort sorting = eventSort.by(Comment::getCreated).descending();
        Pageable pageable = PageRequest.of(from / size, size, sorting);
        return commentRepository.findByEventIdAndEventState(eventId, EventState.PUBLISHED, pageable);
    }

    public Comment create(Long userId, Comment comment, Long eventId) {
        Event event = eventService.findById(eventId);
        if (EventState.PUBLISHED != event.getState()) {
            throw new ConflictException("You can't add comment, because event {0} is not published", eventId);
        }
        User user = userService.findById(userId);
        comment.setEvent(event);
        comment.setAuthor(user);
        return commentRepository.save(comment);
    }

    public Comment update(Long userId, Long commentId, Comment comment) {
        User user = userService.findById(userId);
        Comment savedComment = findById(commentId);
        if (!savedComment.getAuthor().equals(user)) {
            throw new ConflictException("User {0} cannot update comment {1}", userId, commentId);
        }
        if (savedComment.getCreated().isBefore(LocalDateTime.now().minusMinutes(MINUTES_FOR_EDITING))) {
            throw new ConflictException("Editing is available within {0} minutes after creation", MINUTES_FOR_EDITING);
        }
        savedComment.setCommentBody(comment.getCommentBody());
        savedComment.setUpdated(LocalDateTime.now());
        return commentRepository.save(savedComment);
    }

    public Comment findById(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(notFoundException("Comment {0} not found", commentId));
    }

    public void remove(Long userId, Long commentId) {
        User user = userService.findById(userId);
        Comment savedComment = findById(commentId);
        if (!savedComment.getAuthor().equals(user)) {
            throw new ConflictException("User {0} cannot remove comment {1}", userId, commentId);
        }
        commentRepository.delete(savedComment);
    }

    public Comment updateByAdmin(Long commentId, Comment comment) {
        Comment savedComment = findById(commentId);
        savedComment.setCommentBody(comment.getCommentBody());
        return commentRepository.save(savedComment);
    }

    public void removeByAdmin(Long commentId) {
        Comment savedComment = findById(commentId);
        commentRepository.delete(savedComment);
    }
}
