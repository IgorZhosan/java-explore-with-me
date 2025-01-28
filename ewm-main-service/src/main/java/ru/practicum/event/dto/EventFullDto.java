package ru.practicum.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import ru.practicum.User.dto.UserDtoShort;
import ru.practicum.category.dto.CategoryOutputDto;
import ru.practicum.event.model.EventState;
import ru.practicum.event.model.Location;

import java.time.LocalDateTime;

@Data
public class EventFullDto {

    private Long id;
    private UserDtoShort initiator; // Инициатор события
    private CategoryOutputDto category; // Категория события
    private Integer confirmedRequests; //Количество одобренных заявок
    private Location location; // место проведения события
    private String title;
    private String annotation;
    private String description;
    private EventState state;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdOn;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime publishedOn;

    private Integer participantLimit;
    private Boolean paid;
    private Boolean requestModeration;
    private Integer views;
}
