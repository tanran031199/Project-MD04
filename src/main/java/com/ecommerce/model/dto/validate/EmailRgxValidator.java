package com.ecommerce.model.dto.validate;

import com.ecommerce.config.Constant.RegexPattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailRgxValidator implements ConstraintValidator<EmailRgxConstraint, String> {
    @Override
    public void initialize(EmailRgxConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (!value.isEmpty()) {
            return value.matches(RegexPattern.EMAIL.getPattern());
        }

        return true;
    }
}
