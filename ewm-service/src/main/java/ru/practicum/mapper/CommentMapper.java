package ru.practicum.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.dto.comment.CommentDto;
import ru.practicum.dto.comment.NewCommentDto;
import ru.practicum.dto.comment.UpdateCommentDto;
import ru.practicum.model.Comment;

@Mapper
public interface CommentMapper {

    @Mapping(target = "event", ignore = true)
    Comment newCommentDtoMapToComment(NewCommentDto newCommentDto);

    Comment updateCommentDtoMapToComment(UpdateCommentDto updateCommentDto);

    @Mapping(source = "author.id", target = "author")
    @Mapping(source = "event.id", target = "event")
    CommentDto commentMapToDto(Comment comment);

}
