package com.devkind.barebonesystem.validation.constraint;


import com.devkind.barebonesystem.validation.Age;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Date;

public class AgeValidator implements ConstraintValidator<Age, String> {
    private int minAge;
    private String format;

    @Override
    public void initialize(Age constraintAnnotation) {
        this.minAge = constraintAnnotation.minAge();
        this.format = constraintAnnotation.format();
    }

    @Override
    public boolean isValid(String dateOfBirth, ConstraintValidatorContext context) {
        if(dateOfBirth == null) {
            return false;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        dateFormat.setLenient(false); // Disable lenient parsing

        try {
            Date parsedDob = dateFormat.parse(dateOfBirth);
            // Calculate age
            long currentTime = System.currentTimeMillis();
            long dobTime = parsedDob.getTime();
            long diffTime = currentTime - dobTime;
            long ageInMillis = 1000L * 60 * 60 * 24 * 365;
            int age = (int) (diffTime / ageInMillis);

            // Check if age is at least the specified minimum age
            return age >= minAge;

        } catch (ParseException e) {
            return false; // Date string is not in the specified format
        }
    }
}
