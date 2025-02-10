package ru.practicum.comment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.comment.dto.CommentInputDto;
import ru.practicum.comment.dto.CommentOutputDto;
import ru.practicum.comment.mapper.CommentMapper;
import ru.practicum.comment.model.Comment;
import ru.practicum.comment.repository.CommentRepository;
import ru.practicum.event.model.Event;
import ru.practicum.event.model.EventState;
import ru.practicum.event.repository.EventRepository;
import ru.practicum.exception.ConflictException;
import ru.practicum.exception.NotFoundException;
import ru.practicum.user.model.User;
import ru.practicum.user.repository.UserRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final CommentMapper commentMapper;

    /**
     * При GET/UPDATE нужно 404, если комментария нет.
     */
    private Comment getCommentOr404(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("Комментарий не найден")); // код 404
    }

    /**
     * При DELETE несуществующего комментария нужно 500.
     */
    private Comment getCommentOr500(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Нет комментария => 500")); // код 500
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentOutputDto> getAllComments(Long userId, Long eventId, int from, int size) {
        PageRequest pageRequest = PageRequest.of(from / size, size);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден")); // 404
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Событие не найдено")); // 404
        List<Comment> comments = commentRepository.findByAuthorAndEvent(user, event, pageRequest);
        if (comments.isEmpty()) {
            return new ArrayList<>();
        }
        return commentMapper.toCommentOutputDtoList(comments);
    }

    @Override
    public CommentOutputDto createComment(CommentInputDto commentInputDto, Long userId, Long eventId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден")); // 404
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Событие не найдено")); // 404
        if (event.getState() != EventState.PUBLISHED) {
            throw new ConflictException(""); // 409
        }
        Comment comment = commentMapper.toComment(commentInputDto, user, event);
        return commentMapper.toCommentOutputDto(commentRepository.save(comment));
    }

    @Override
    public CommentOutputDto updateComment(CommentInputDto commentInputDto, Long userId, Long commentId) {
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException("Пользователь не найден"); // 404
        }
        Comment oldComment = getCommentOr404(commentId); // 404, если не существует
        if (!oldComment.getAuthor().getId().equals(userId)) {
            throw new ConflictException(""); // 409
        }
        oldComment.setText(commentInputDto.getText());
        return commentMapper.toCommentOutputDto(oldComment);
    }

    @Override
    public void deleteComment(Long userId, Long commentId) {
        Comment comment = getCommentOr500(commentId); // 500, если не существует
        if (!comment.getAuthor().getId().equals(userId)
                && !comment.getEvent().getInitiator().getId().equals(userId)) {
            throw new ConflictException(""); // 409
        }
        commentRepository.deleteById(commentId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentOutputDto> getAllCommentsByEvent(Long eventId, int from, int size) {
        PageRequest pageRequest = PageRequest.of(from / size, size);
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Событие не найдено")); // 404
        List<Comment> comments = commentRepository.findByEvent(event, pageRequest);
        if (comments.isEmpty()) {
            return new ArrayList<>();
        }
        return commentMapper.toCommentOutputDtoList(comments);
    }

    @Override
    @Transactional(readOnly = true)
    public CommentOutputDto getCommentById(Long commentId) {
        Comment comment = getCommentOr404(commentId); // 404, если не существует
        return commentMapper.toCommentOutputDto(comment);
    }

    @Override
    public void deleteCommentByAdmin(Long eventId) {
        commentRepository.deleteById(eventId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentOutputDto> searchComments(Long userId, Long eventId, String text, Integer from, Integer size) {
        PageRequest pageRequest = PageRequest.of(from / size, size);
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException("Пользователь не найден"); // 404
        }
        if (!eventRepository.existsById(eventId)) {
            throw new NotFoundException("Событие не найдено"); // 404
        }
        if (text.isBlank()) {
            return Collections.emptyList();
        }
        List<Comment> comments = commentRepository
                .findAllByAuthorIdAndEventIdAndTextContainingIgnoreCase(userId, eventId, text, pageRequest);
        return commentMapper.toCommentOutputDtoList(comments);
    }
}