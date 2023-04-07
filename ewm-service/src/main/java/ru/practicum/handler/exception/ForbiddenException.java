package ru.practicum.handler.exception;

public class ForbiddenException extends EwmServiceException {
    public ForbiddenException(String message, Object... args) {
        super(message, args);
    }
}
