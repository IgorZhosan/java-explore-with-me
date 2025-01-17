package ru.practicum;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatDtoInput {

    @NotBlank
    @Size(max = 255)
    private String app; //Идентификатор сервиса для которого записывается информация

    @NotBlank
    @NotEmpty
    @Size(max = 255)
    private String uri; //URI для которого был осуществлен запрос

    @NotBlank
    @Size(max = 255)
    private String ip; //IP-адрес пользователя, осуществившего запрос

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalDateTime timestamp; //Дата и время, когда был совершен запрос к эндпоинту (в формате "yyyy-MM-dd HH:mm:ss")
}
