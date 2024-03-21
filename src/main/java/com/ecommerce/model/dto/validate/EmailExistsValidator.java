package com.ecommerce.model.dto.validate;

import com.ecommerce.config.Constant.RegexPattern;
import com.ecommerce.model.dao.design.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailExistsValidator implements ConstraintValidator<EmailExistsConstraint, String> {
    @Autowired
    private UserDAO userDAO;

    @Override
    public void initialize(EmailExistsConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (!value.isEmpty() && value.matches(RegexPattern.EMAIL.getPattern())) {
            return !userDAO.checkExistsEmail(value);
        }

        return true;
    }
}
