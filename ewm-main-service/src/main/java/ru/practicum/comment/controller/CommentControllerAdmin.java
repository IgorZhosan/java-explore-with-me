package ru.practicum.comment.controller;

import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.comment.service.CommentService;

/**
 * Административный контроллер для управления комментариями.
 */
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/comments")
public class CommentControllerAdmin {

    private final CommentService commentService;

    /**
     * Удаляет все комментарии к указанному событию (или комментарий/комментарии,
     * если в сервисе реализована более конкретная логика).
     *
     * @param eventId ID события, комментарии к которому нужно удалить
     */
    @DeleteMapping("/events/{eventId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCommentByAdmin(@PathVariable @Positive Long eventId) {
        commentService.deleteCommentByAdmin(eventId);
    }
}