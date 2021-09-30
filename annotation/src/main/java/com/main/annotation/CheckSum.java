package com.main.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = CheckSumAnnotationValidator.class)
@Target({ ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckSum {
    String message() default "Неправильная чек сумма кода топливного чека";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
