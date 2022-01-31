package com.learnjava.bookshelf.controller;

import com.learnjava.bookshelf.dto.ErrorResponse;
import com.learnjava.bookshelf.exception.ItemNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;

@RestControllerAdvice
@Slf4j
public class ErrorController {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    public ErrorResponse handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        log.warn("Handling {} exception from {}", "MethodArgumentTypeMismatch", e.getParameter());
        return new ErrorResponse(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = ItemNotFoundException.class)
    public ErrorResponse handleItemNotFoundException(ItemNotFoundException e) {
        log.info(e.getMessage());
        return new ErrorResponse(LocalDateTime.now(),HttpStatus.NOT_FOUND.value(), e.getMessage());
    }
}
