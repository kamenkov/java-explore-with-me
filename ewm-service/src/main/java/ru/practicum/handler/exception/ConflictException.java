package ru.practicum.handler.exception;

import java.util.function.Supplier;

public class ConflictException extends EwmServiceException {

    public ConflictException(String message, Object... args) {
        super(message, args);
    }

    public static Supplier<ConflictException> conflictException(String message, Object... args) {
        return () -> new ConflictException(message, args);
    }
}
