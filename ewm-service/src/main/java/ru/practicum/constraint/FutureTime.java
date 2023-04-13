package ru.practicum.constraint;

import ru.practicum.constraint.validator.FutureTimeValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FutureTimeValidator.class)
public @interface FutureTime {

    String message() default "The date must be in the future and at least {hours} hours from now";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    int hours() default 1;
}

