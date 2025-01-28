package ru.practicum.exception;

public class ValidationException extends RuntimeException {

    public ValidationException(final String message) {
        super(message);
    }
}