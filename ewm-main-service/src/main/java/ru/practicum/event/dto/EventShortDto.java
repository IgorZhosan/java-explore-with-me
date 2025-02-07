package ru.practicum.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import ru.practicum.User.dto.UserDtoShort;
import ru.practicum.category.dto.CategoryOutputDto;

import java.time.LocalDateTime;

import static ru.practicum.event.dto.EventFullDto.DATE_TIME_FORMAT;

@Data
public class EventShortDto {

    private Long id;

    private UserDtoShort initiator;

    private CategoryOutputDto category;

    private Integer confirmedRequests;

    private String title;

    private String annotation;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_FORMAT)
    private LocalDateTime eventDate;

    private Boolean paid;

    private Integer views;
}