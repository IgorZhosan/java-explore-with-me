package ru.practicum.comment.controller;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.comment.dto.CommentInputDto;
import ru.practicum.comment.dto.CommentOutputDto;
import ru.practicum.comment.service.CommentService;

import java.util.List;

/**
 * Контроллер для управления комментариями от имени конкретного пользователя.
 */
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/users/{userId}")
public class CommentControllerPrivate {

    private final CommentService commentService;

    /**
     * Получить все комментарии конкретного пользователя к событию.
     *
     * @param userId  ID пользователя
     * @param eventId ID события
     * @param offset  начальный индекс для пагинации
     * @param limit   количество элементов для выборки
     * @return список комментариев
     */
    @GetMapping("/events/{eventId}/comments")
    @ResponseStatus(HttpStatus.OK)
    public List<CommentOutputDto> getAllComments(@PathVariable @Positive Long userId,
                                                 @PathVariable @Positive Long eventId,
                                                 @RequestParam(defaultValue = "0") int offset,
                                                 @RequestParam(defaultValue = "10") int limit) {
        return commentService.getAllComments(userId, eventId, offset, limit);
    }

    /**
     * Создать новый комментарий к событию.
     *
     * @param commentInputDto тело комментария
     * @param userId          ID пользователя
     * @param eventId         ID события
     * @return созданный комментарий
     */
    @PostMapping("/events/{eventId}/comments")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentOutputDto createComment(@RequestBody @Validated CommentInputDto commentInputDto,
                                          @PathVariable @Positive Long userId,
                                          @PathVariable @Positive Long eventId) {
        return commentService.createComment(commentInputDto, userId, eventId);
    }

    /**
     * Обновить существующий комментарий.
     *
     * @param commentInputDto новые данные для комментария
     * @param userId          ID пользователя
     * @param commentId       ID комментария
     * @return обновлённый комментарий
     */
    @PatchMapping("/comments/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public CommentOutputDto updateComment(@RequestBody @Validated CommentInputDto commentInputDto,
                                          @PathVariable @Positive Long userId,
                                          @PathVariable @Positive Long commentId) {
        return commentService.updateComment(commentInputDto, userId, commentId);
    }

    /**
     * Удалить комментарий.
     *
     * @param userId    ID пользователя
     * @param commentId ID комментария
     */
    @DeleteMapping("/comments/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable @Positive Long userId,
                              @PathVariable @Positive Long commentId) {
        commentService.deleteComment(userId, commentId);
    }

    /**
     * Поиск комментариев по тексту.
     *
     * @param userId  ID пользователя
     * @param eventId ID события
     * @param text    текст для поиска
     * @param offset  начальный индекс для пагинации
     * @param limit   количество элементов для выборки
     * @return список подходящих по условию комментариев
     */
    @GetMapping("/events/{eventId}/comments/search")
    @ResponseStatus(HttpStatus.OK)
    public List<CommentOutputDto> searchComments(@PathVariable Long userId,
                                                 @PathVariable Long eventId,
                                                 @RequestParam @NotBlank String text,
                                                 @RequestParam(defaultValue = "0") @PositiveOrZero Integer offset,
                                                 @RequestParam(defaultValue = "10") @Positive Integer limit) {
        return commentService.searchComments(userId, eventId, text, offset, limit);
    }
}