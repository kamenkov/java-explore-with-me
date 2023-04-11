package ru.practicum.handler;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.List;

public class ValidationErrorResponse {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime timestamp = LocalDateTime.now();
    private final int status;
    private final List<Violation> violations;

    public ValidationErrorResponse(int status, List<Violation> violations) {
        this.status = status;
        this.violations = violations;
    }

    public List<Violation> getViolations() {
        return violations;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public int getStatus() {
        return status;
    }
}
