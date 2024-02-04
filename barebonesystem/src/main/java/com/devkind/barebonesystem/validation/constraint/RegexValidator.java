package com.devkind.barebonesystem.validation.constraint;

import com.devkind.barebonesystem.validation.Regex;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexValidator implements ConstraintValidator<Regex, String> {
    private String pattern;
    private boolean isRequired;

    @Override
    public void initialize(Regex constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        this.pattern = constraintAnnotation.pattern();
        this.isRequired = constraintAnnotation.isRequired();

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        Pattern patternComplied = Pattern.compile(pattern);
        Matcher matcher = patternComplied.matcher(value);
        if(isRequired || StringUtils.isNotBlank(value)) {
            return matcher.matches();
        }
        return true;
    }
}

