package dev.rogueai.collection.controller.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = IsbnValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface IsbnConstraint {
    String message() default "{validation.constraints.Isbn.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}



