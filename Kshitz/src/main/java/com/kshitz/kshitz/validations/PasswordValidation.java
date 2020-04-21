package com.kshitz.kshitz.validations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class PasswordValidation implements ConstraintValidator<ValidPassword, String> {
    //not required to initialize any service now
    @Override
    public void initialize(ValidPassword password) {
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {

        if (password == null) {
            return false;
        }
        return password.matches("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})");

    }
}