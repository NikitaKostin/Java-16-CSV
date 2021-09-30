package com.main.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FuelCheckAnnotationValidator implements ConstraintValidator<FuelCheckIsValid, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null){
            return false;
        }
        return value.matches("azk-[0-9]{10}-[0-9]{2}");
    }
}
