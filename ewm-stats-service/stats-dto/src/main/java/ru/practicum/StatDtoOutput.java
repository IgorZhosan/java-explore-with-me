package ru.practicum;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StatDtoOutput {

    private String app; //Название сервиса

    private String uri; //URI сервиса

    private Long hits; //Количество просмотров
}