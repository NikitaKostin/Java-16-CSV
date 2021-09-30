package com.main.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CheckSumAnnotationValidator implements ConstraintValidator<CheckSum, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null){
            return false;
        }

        var splitValue = value.split("-");
        var result = splitValue[1].chars().map(Character::getNumericValue).sum();

        return result != 0 && result == Integer.parseInt(splitValue[2]);
    }
}
