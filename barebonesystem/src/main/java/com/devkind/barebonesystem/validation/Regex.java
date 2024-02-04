package com.devkind.barebonesystem.validation;

import com.devkind.barebonesystem.validation.constraint.RegexValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;


/*
* Customize Regex validation
* */
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = RegexValidator.class)
public @interface Regex {
    String message() default "Input did not match the required format!";
    String pattern() default "";
    boolean isRequired() default true;

    // represents group of constraints
    Class<?>[] groups() default {};

    // represents additional information about annotation
    Class<? extends Payload>[] payload() default {};

}
