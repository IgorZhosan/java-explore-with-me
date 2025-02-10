package ru.practicum.comment.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.User.model.User;
import ru.practicum.comment.dto.CommentInputDto;
import ru.practicum.comment.dto.CommentOutputDto;
import ru.practicum.comment.model.Comment;
import ru.practicum.event.mapper.EventMapper;
import ru.practicum.event.model.Event;

import java.util.List;

@Mapper(componentModel = "spring", uses = {EventMapper.class})
public interface CommentMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "text", source = "commentInputDto.text")
    @Mapping(target = "event", source = "event")
    @Mapping(target = "author", source = "user")
    @Mapping(target = "created", expression = "java(java.time.LocalDateTime.now())")
    Comment toComment(CommentInputDto commentInputDto, User user, Event event);

    @Mapping(target = "event", source = "comment.event", qualifiedByName = "toEventShortDto")
    @Mapping(target = "authorName", source = "author.name")
    CommentOutputDto toCommentOutputDto(Comment comment);

    List<CommentOutputDto> toCommentOutputDtoList(List<Comment> comments);
}
