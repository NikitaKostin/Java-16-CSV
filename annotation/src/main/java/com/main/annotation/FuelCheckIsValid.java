package com.main.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Constraint(validatedBy = FuelCheckAnnotationValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@CheckSum
public @interface FuelCheckIsValid {
    String message() default "Неправильный формат кода топливного чека";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
