package ru.practicum.handler.exception;

import java.util.function.Supplier;

public class BadRequestException extends EwmServiceException {

    public BadRequestException(String message, Object... args) {
        super(message, args);
    }

    public static Supplier<BadRequestException> badRequestException(String message, Object... args) {
        return () -> new BadRequestException(message, args);
    }
}
