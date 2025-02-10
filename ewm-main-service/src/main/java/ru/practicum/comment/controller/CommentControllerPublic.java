package ru.practicum.comment.controller;

import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.comment.dto.CommentOutputDto;
import ru.practicum.comment.service.CommentService;

import java.util.List;

/**
 * Публичный контроллер для работы с комментариями.
 */
@Validated
@RestController
@RequestMapping
@RequiredArgsConstructor
public class CommentControllerPublic {

    private final CommentService commentService;

    /**
     * Получить все комментарии для указанного события.
     *
     * @param eventId ID события
     * @param offset  индекс первого элемента для пагинации
     * @param limit   максимальное количество комментариев для возврата
     * @return список комментариев
     */
    @GetMapping("events/{eventId}/comments")
    @ResponseStatus(HttpStatus.OK)
    public List<CommentOutputDto> getAllCommentsByEvent(@PathVariable Long eventId,
                                                        @RequestParam(defaultValue = "0") int offset,
                                                        @RequestParam(defaultValue = "10") int limit) {
        return commentService.getAllCommentsByEvent(eventId, offset, limit);
    }

    /**
     * Получить комментарий по его идентификатору.
     *
     * @param commentId ID комментария
     * @return найденный комментарий
     */
    @GetMapping("/comments/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public CommentOutputDto getCommentById(@PathVariable @Positive Long commentId) {
        return commentService.getCommentById(commentId);
    }
}