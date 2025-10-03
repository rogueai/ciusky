package dev.rogueai.ciusky.controller.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface IsbnFormat {

    String pattern() default "(\\d{3})(\\d{1})(\\d{3})(\\d{5})(\\d{1})";

}
