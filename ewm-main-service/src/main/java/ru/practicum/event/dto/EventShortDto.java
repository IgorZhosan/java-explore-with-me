package ru.practicum.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import ru.practicum.User.dto.UserDtoShort;

import ru.practicum.category.dto.CategoryOutputDto;

import java.time.LocalDateTime;

@Data
public class EventShortDto {

    private Long id;

    private UserDtoShort initiator; // Инициатор события

    private CategoryOutputDto category; // Категория события

    private Integer confirmedRequests; //Количество одобренных заявок на участие в данном событии

    private String title; // Название события

    private String annotation;  // Аннотация события

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate; // Дата и время события

    private Boolean paid; // Платное событие

    private Integer views; // Количество просмотров события
}
