package com.devkind.barebonesystem.validation.constraint;

import com.devkind.barebonesystem.validation.DateFormat;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateFormatValidator implements ConstraintValidator<DateFormat, String> {

    private String format;

    @Override
    public void initialize(DateFormat constraintAnnotation) {
        this.format = constraintAnnotation.format();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // Null values are considered valid
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        dateFormat.setLenient(false); // Disable lenient parsing

        try {
            dateFormat.parse(value);
            return true; // Date string is valid according to the specified format
        } catch (ParseException e) {
            return false; // Date string is not in the specified format
        }
    }
}
