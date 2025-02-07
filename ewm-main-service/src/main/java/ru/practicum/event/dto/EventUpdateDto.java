package ru.practicum.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;
import ru.practicum.event.model.Location;
import ru.practicum.event.model.StateAction;

import java.time.LocalDateTime;

import static ru.practicum.event.dto.EventFullDto.DATE_TIME_FORMAT;

@Data
public class EventUpdateDto {

    @Size(min = 20, max = 2000)
    private String annotation;

    @PositiveOrZero
    private Long category;

    @Size(min = 20, max = 7000)
    private String description;

    @Future
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_FORMAT)
    private LocalDateTime eventDate;

    private Location location;

    private Boolean paid;

    @PositiveOrZero
    private Integer participantLimit;

    private Boolean requestModeration;

    private StateAction stateAction;

    @Size(min = 3, max = 120)
    private String title;
}