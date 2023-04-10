package ru.practicum.constraint.validator;

import ru.practicum.constraint.FutureTime;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;

public class FutureTimeValidator implements ConstraintValidator<FutureTime, LocalDateTime> {

    private int hours;

    @Override
    public void initialize(FutureTime constraintAnnotation) {
        this.hours = constraintAnnotation.hours();
    }

    @Override
    public boolean isValid(LocalDateTime value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime minDate = now.plusHours(hours);

        return value.isAfter(minDate);
    }
}
