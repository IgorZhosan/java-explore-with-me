package ru.practicum.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.event.dto.EventShortDto;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentOutputDto {
    private Long id;

    private String text;

    private String authorName;

    private EventShortDto event;

    private LocalDateTime created;
}
