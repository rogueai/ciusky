package dev.rogueai.ciusky.controller.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = TagValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface TagConstraint {

    boolean acceptMany() default false;

    String message() default "{validation.constraints.tag.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
