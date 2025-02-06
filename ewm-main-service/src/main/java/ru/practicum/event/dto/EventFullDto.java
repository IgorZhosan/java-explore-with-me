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

    private Integer confirmedRequests; //Количество одобренных заявок на участие в данном событии

    private Location location; // место проведения события

    private String title; // Название события

    private String annotation;  // Аннотация события

    private String description; // Описание события

    private EventState state; // Состояние события

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate; // Дата и время события

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdOn; // Дата и время создания события

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime publishedOn; // Дата и время публикации события

    private Integer participantLimit; // Лимит участников

    private Boolean paid; // Платное событие

    private Boolean requestModeration; // Модерация заявок

    Integer views; // Количество просмотров события
}
