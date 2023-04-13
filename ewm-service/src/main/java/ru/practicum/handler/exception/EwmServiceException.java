package ru.practicum.handler.exception;

import java.text.MessageFormat;

public abstract class EwmServiceException extends RuntimeException {

    protected EwmServiceException(String message, Object... args) {
        super(MessageFormat.format(message, args));
    }
}
