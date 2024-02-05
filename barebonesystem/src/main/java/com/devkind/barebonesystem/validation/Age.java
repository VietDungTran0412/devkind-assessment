package com.devkind.barebonesystem.validation;

import com.devkind.barebonesystem.validation.constraint.AgeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/*
 * Customize Regex validation
 * */
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = AgeValidator.class)
public @interface Age {
    String message() default "Invalid age input!";
    boolean isRequired() default true;
    String format() default "yyyy-MM-dd";

    // represents group of constraints
    Class<?>[] groups() default {};

    // represents additional information about annotation
    Class<? extends Payload>[] payload() default {};

    int minAge() default 18;
}
