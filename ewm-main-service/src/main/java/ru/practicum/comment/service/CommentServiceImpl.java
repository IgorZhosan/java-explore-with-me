package ru.practicum.comment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.user.model.User;
import ru.practicum.user.repository.UserRepository;
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

    @Override
    @Transactional(readOnly = true)
    public List<CommentOutputDto> getAllComments(Long userId, Long eventId, int from, int size) {
        PageRequest pageRequest = PageRequest.of(from / size, size);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с id = " + userId + " не существует."));
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Событие с id = " + eventId + " не существует."));

        List<Comment> comments = commentRepository.findByAuthorAndEvent(user, event, pageRequest);
        if (comments.isEmpty()) {
            log.info("У пользователя с id={} нет комментариев к событию с id={}.", userId, eventId);
            return new ArrayList<>();
        }

        log.info("Получен список комментариев пользователя с id={} к событию с id={}.", userId, eventId);
        return commentMapper.toCommentOutputDtoList(comments);
    }

    @Override
    public CommentOutputDto createComment(CommentInputDto commentInputDto, Long userId, Long eventId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с id = " + userId + " не существует."));
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Событие с id = " + eventId + " не существует."));

        if (event.getState() != EventState.PUBLISHED) {
            throw new ConflictException("Невозможно написать комментарий к неопубликованному событию.");
        }

        Comment newComment = commentMapper.toComment(commentInputDto, user, event);
        Comment savedComment = commentRepository.save(newComment);

        log.info("Комментарий к событию с id={} добавлен пользователем с id={}.", eventId, userId);
        return commentMapper.toCommentOutputDto(savedComment);
    }

    @Override
    public CommentOutputDto updateComment(CommentInputDto commentInputDto, Long userId, Long commentId) {
        Comment oldComment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("Комментарий с id = " + commentId + " не существует."));

        if (!userRepository.existsById(userId)) {
            throw new NotFoundException("Пользователь с id = " + userId + " не существует.");
        }

        if (!oldComment.getAuthor().getId().equals(userId)) {
            throw new ConflictException("Редактирование доступно только автору комментария.");
        }

        oldComment.setText(commentInputDto.getText());

        log.info("Комментарий с id={} обновлён пользователем с id={}.", commentId, userId);
        return commentMapper.toCommentOutputDto(oldComment);
    }

    @Override
    public void deleteComment(Long userId, Long commentId) {

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("Комментарий с id = " + commentId + " не существует."));


        if (!comment.getAuthor().getId().equals(userId) &&
                !comment.getEvent().getInitiator().getId().equals(userId)) {
            throw new ConflictException("Удалять комментарий может только его автор или инициатор события.");
        }

        commentRepository.deleteById(commentId);
        log.info("Комментарий с id={} удалён пользователем с id={}.", commentId, userId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentOutputDto> getAllCommentsByEvent(Long eventId, int from, int size) {
        PageRequest pageRequest = PageRequest.of(from / size, size);

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Событие с id = " + eventId + " не существует."));

        List<Comment> comments = commentRepository.findByEvent(event, pageRequest);
        if (comments.isEmpty()) {
            log.info("У события с id={} нет комментариев.", eventId);
            return new ArrayList<>();
        }

        log.info("Получен список комментариев к событию с id={}.", eventId);
        return commentMapper.toCommentOutputDtoList(comments);
    }

    @Override
    @Transactional(readOnly = true)
    public CommentOutputDto getCommentById(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("Комментарий с id = " + commentId + " не существует."));
        log.info("Получен комментарий по id={}.", commentId);
        return commentMapper.toCommentOutputDto(comment);
    }

    @Override
    public void deleteCommentByAdmin(Long eventId) {

        commentRepository.deleteById(eventId);
        log.info("Комментарий, связанный с событием id={}, удалён администратором.", eventId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentOutputDto> searchComments(Long userId, Long eventId, String text, Integer from, Integer size) {
        PageRequest pageRequest = PageRequest.of(from / size, size);

        if (!userRepository.existsById(userId)) {
            throw new NotFoundException("Пользователь с id = " + userId + " не существует.");
        }
        if (!eventRepository.existsById(eventId)) {
            throw new NotFoundException("Событие с id = " + eventId + " не существует.");
        }

        if (text.isBlank()) {
            return Collections.emptyList();
        }

        List<Comment> comments = commentRepository
                .findAllByAuthorIdAndEventIdAndTextContainingIgnoreCase(userId, eventId, text, pageRequest);

        log.info("Поиск комментариев по тексту '{}' для userId={}, eventId={}. Найдено: {}",
                text, userId, eventId, comments.size());

        return commentMapper.toCommentOutputDtoList(comments);
    }
}