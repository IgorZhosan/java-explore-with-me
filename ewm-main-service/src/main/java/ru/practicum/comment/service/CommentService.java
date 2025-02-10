package ru.practicum.comment.service;

import ru.practicum.comment.dto.CommentInputDto;
import ru.practicum.comment.dto.CommentOutputDto;

import java.util.List;

public interface CommentService {

    List<CommentOutputDto> getAllComments(Long userId, Long eventId, int from, int size);

    CommentOutputDto createComment(CommentInputDto commentInputDto, Long userId, Long eventId);

    CommentOutputDto updateComment(CommentInputDto commentInputDto, Long userId, Long commentId);

    void deleteComment(Long userId, Long commentId);

    List<CommentOutputDto> getAllCommentsByEvent(Long eventId, int from, int size);

    CommentOutputDto getCommentById(Long commentId);

    void deleteCommentByAdmin(Long eventId);

    List<CommentOutputDto> searchComments(Long userId, Long eventId, String text, Integer from, Integer size);
}