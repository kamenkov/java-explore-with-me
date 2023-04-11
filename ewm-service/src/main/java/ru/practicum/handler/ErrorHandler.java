package ru.practicum.handler;

import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.handler.exception.BadRequestException;
import ru.practicum.handler.exception.ConflictException;
import ru.practicum.handler.exception.NotFoundException;

import java.util.stream.Collectors;

@RestControllerAdvice
public class ErrorHandler {

    private static final String BAD_REQUEST_LOG_PREFIX = "[BAD REQUEST]: {}";
    private static final String CONFLICT_LOG_PREFIX = "[CONFLICT]: {}";
    private final Logger log = LoggerFactory.getLogger(getClass());

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFoundException(final NotFoundException e) {
        log.debug("[NOT FOUND]: {}", e.getMessage());
        return new ErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND.value());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleBadRequestException(final BadRequestException e) {
        log.debug(BAD_REQUEST_LOG_PREFIX, e.getMessage());
        return new ErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleIllegalArgumentException(final IllegalArgumentException e) {
        log.debug(BAD_REQUEST_LOG_PREFIX, e.getMessage());
        return new ErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleConstraintViolationException(final ConstraintViolationException e) {
        log.debug(BAD_REQUEST_LOG_PREFIX, e.getMessage());
        return new ErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMissingServletRequestParameterException(final MissingServletRequestParameterException e) {
        log.debug(BAD_REQUEST_LOG_PREFIX, e.getMessage());
        return new ErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationErrorResponse handleMethodArgumentNotValidException(final MethodArgumentNotValidException e) {
        log.debug(BAD_REQUEST_LOG_PREFIX, e.getMessage());
        return new ValidationErrorResponse(
                HttpStatus.BAD_REQUEST.value(), e.getBindingResult().getFieldErrors().stream()
                .map(error -> new Violation(error.getField(), error.getDefaultMessage()))
                .collect(Collectors.toList())
        );
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleConflictException(final ConflictException e) {
        log.debug(CONFLICT_LOG_PREFIX, e.getMessage());
        return new ErrorResponse(e.getMessage(), HttpStatus.CONFLICT.value());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleDataIntegrityViolationException(final DataIntegrityViolationException e) {
        log.debug(CONFLICT_LOG_PREFIX, e.getMessage());
        return new ErrorResponse(e.getMessage(), HttpStatus.CONFLICT.value());
    }

}
