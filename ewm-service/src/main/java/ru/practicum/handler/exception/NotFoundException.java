package ru.practicum.handler.exception;

import java.util.function.Supplier;

public class NotFoundException extends EwmServiceException {

    public NotFoundException(String message, Object... args) {
        super(message, args);
    }

    public static Supplier<NotFoundException> notFoundException(String message, Object... args) {
        return () -> new NotFoundException(message, args);
    }
}
