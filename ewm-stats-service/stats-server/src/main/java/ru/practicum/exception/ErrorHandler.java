package ru.practicum.exception;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler({MethodArgumentNotValidException.class, ConstraintViolationException.class, ValidationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST) //400
    public Map<String, String> handleValidation(Exception e) {
        log.warn("Получен статус 400 Bad Request {}", e.getMessage(), e);
        return Map.of("error", e.getMessage());
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleMissingParam(MissingServletRequestParameterException e) {
        log.warn("Получен статус 400 Bad Request (отсутствует параметр): {}", e.getMessage(), e);
        return Map.of("error", "Parameter '" + e.getParameterName() + "' is missing or invalid");
    }

    @ExceptionHandler //404
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleNotFound(NotFoundException e) {
        log.warn("Получен статус 404 Not Found {}", e.getMessage(), e);
        return Map.of("error", e.getMessage());
    }

    @ExceptionHandler //500
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, String> handleInternalServerError(Throwable e) {
        log.error("Получен статус 500 Internal Server Error {}", e.getMessage(), e);
        return Map.of("error", e.getMessage());
    }
}
