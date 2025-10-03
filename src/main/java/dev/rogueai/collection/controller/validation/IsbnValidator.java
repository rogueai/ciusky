package dev.rogueai.collection.controller.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.nio.charset.StandardCharsets;

public class IsbnValidator implements ConstraintValidator<IsbnConstraint, String> {

    public static int checksumIsbn13(byte[] isbn) {
        int checksum = 0;
        for (int i = 0; i < 12; i++) {
            if (i % 2 == 0) {
                checksum += (isbn[i] - '0');
            } else {
                checksum += (isbn[i] - '0') * 3;
            }
        }
        return 10 - (checksum % 10);
    }

    @Override
    public boolean isValid(String isbn, ConstraintValidatorContext constraintValidatorContext) {
        if (isbn == null) {
            return true;
        }
        if (isbn.length() == 13) {
            byte[] bytes = isbn.getBytes(StandardCharsets.UTF_8);
            int checksum = checksumIsbn13(bytes);
            return Character.getNumericValue(isbn.charAt(12)) == checksum;
        }
        return false;
    }
}
