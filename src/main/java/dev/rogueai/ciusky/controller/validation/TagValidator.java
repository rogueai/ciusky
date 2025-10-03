package dev.rogueai.ciusky.controller.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;

import java.util.StringTokenizer;

public class TagValidator implements ConstraintValidator<TagConstraint, String> {

    private boolean acceptMany;

    @Override
    public void initialize(TagConstraint constraintAnnotation) {
        acceptMany = constraintAnnotation.acceptMany();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s == null || s.isBlank()) {
            return true;
        }

        StringTokenizer stringTokenizer = new StringTokenizer(s, " \t\n\r\f");
        
        if (!acceptMany && stringTokenizer.countTokens() > 1) {
            return false;
        }

        while (stringTokenizer.hasMoreTokens()) {
            String token = stringTokenizer.nextToken();
            if (StringUtils.isAlphanumeric(token) || token.contains(":")) {
                StringTokenizer tagTokenizer = new StringTokenizer(token, ":");
                if (tagTokenizer.countTokens() == 0 || tagTokenizer.countTokens() > 2) {
                    return false;
                }
            } else {
                return false;
            }
        }
        return true;
    }

}
