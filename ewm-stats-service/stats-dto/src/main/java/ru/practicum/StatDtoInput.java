package ru.practicum;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StatDtoInput {

    private static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    @NotBlank
    @Size(max = 255)
    private String app; //Идентификатор сервиса для которого записывается информация

    @NotBlank
    @Size(max = 255)
    private String uri; //URI для которого был осуществлен запрос

    @NotBlank
    @Size(max = 255)
    private String ip; //IP-адрес пользователя, осуществившего запрос

    @NotNull
    @JsonFormat(pattern = DATE_TIME_PATTERN, shape = JsonFormat.Shape.STRING)
    private LocalDateTime timestamp; //Дата и время, когда был совершен запрос к эндпоинту (в формате "yyyy-MM-dd HH:mm:ss")
}