package ru.practicum.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.event.dto.EventFullDto.DATE_TIME_FORMAT;

@Data
@Builder
public class ApiError {

    private final List<String> errors;
    private final String message;
    private final String reason;
    private final String status;

    @JsonFormat(pattern = DATE_TIME_FORMAT)
    private final LocalDateTime timestamp;
}