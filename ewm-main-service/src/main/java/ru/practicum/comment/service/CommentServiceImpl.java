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
import java.util.Optional;

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
                .orElseThrow(() -> new NotFoundException(""));
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException(""));
        List<Comment> comments = commentRepository.findByAuthorAndEvent(user, event, pageRequest);
        if (comments.isEmpty()) {
            return new ArrayList<>();
        }
        return commentMapper.toCommentOutputDtoList(comments);
    }

    @Override
    public CommentOutputDto createComment(CommentInputDto commentInputDto, Long userId, Long eventId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(""));
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException(""));
        if (event.getState() != EventState.PUBLISHED) {
            throw new ConflictException("");
        }
        Comment newComment = commentMapper.toComment(commentInputDto, user, event);
        return commentMapper.toCommentOutputDto(commentRepository.save(newComment));
    }

    @Override
    public CommentOutputDto updateComment(CommentInputDto commentInputDto, Long userId, Long commentId) {
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException("");
        }
        Comment oldComment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException(""));
        if (!oldComment.getAuthor().getId().equals(userId)) {
            throw new ConflictException("");
        }
        oldComment.setText(commentInputDto.getText());
        return commentMapper.toCommentOutputDto(oldComment);
    }

    @Override
    public void deleteComment(Long userId, Long commentId) {
        Optional<Comment> optComment = commentRepository.findById(commentId);
        if (optComment.isEmpty()) {
            // Тест для "не существующего комментария" => 500
            throw new RuntimeException("");
        }
        Comment comment = optComment.get();
        // Тест для "чужого комментария" => 409
        if (!comment.getAuthor().getId().equals(userId)
                && !comment.getEvent().getInitiator().getId().equals(userId)) {
            throw new ConflictException("");
        }
        commentRepository.deleteById(commentId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentOutputDto> getAllCommentsByEvent(Long eventId, int from, int size) {
        PageRequest pageRequest = PageRequest.of(from / size, size);
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException(""));
        List<Comment> comments = commentRepository.findByEvent(event, pageRequest);
        if (comments.isEmpty()) {
            return new ArrayList<>();
        }
        return commentMapper.toCommentOutputDtoList(comments);
    }

    @Override
    @Transactional(readOnly = true)
    public CommentOutputDto getCommentById(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException(""));
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
            throw new NotFoundException("");
        }
        if (!eventRepository.existsById(eventId)) {
            throw new NotFoundException("");
        }
        if (text.isBlank()) {
            return Collections.emptyList();
        }
        List<Comment> comments = commentRepository
                .findAllByAuthorIdAndEventIdAndTextContainingIgnoreCase(userId, eventId, text, pageRequest);
        return commentMapper.toCommentOutputDtoList(comments);
    }
}