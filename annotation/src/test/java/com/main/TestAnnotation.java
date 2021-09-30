package com.main;

import com.main.model.FuelCheck;
import org.junit.Test;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

public class TestAnnotation {

    @Test
    public void test(){
        Validator validator = getValidator();

        var asd = new FuelCheck(1L, "azk-1000000000-01");
        validator.validate(asd).forEach(System.out::println);

    }

    private static Validator getValidator() {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        return validatorFactory.getValidator();
    }
}
