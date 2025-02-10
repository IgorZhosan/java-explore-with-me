package ru.practicum.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class CommentNotFound500Exception extends RuntimeException {
    public CommentNotFound500Exception(String message) {
        super(message);
    }
}
