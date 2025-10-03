package dev.rogueai.ciusky.controller.validation;

import org.jetbrains.annotations.NotNull;
import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Parser;
import org.springframework.format.Printer;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class IsbnFormatter implements AnnotationFormatterFactory<IsbnFormat> {

    private static final String ISBN_13_PREFIX = "978";

    @NotNull
    @Override
    public Set<Class<?>> getFieldTypes() {
        return Set.of(String.class);
    }

    @NotNull
    @Override
    public Printer<String> getPrinter(@NotNull IsbnFormat annotation, @NotNull Class<?> fieldType) {
        return new Printer<>() {

            @NotNull
            @Override
            public String print(@NotNull String value, @NotNull Locale locale) {
                final Pattern pattern = Pattern.compile(annotation.pattern());
                final Matcher matcher = pattern.matcher(value);
                if (matcher.find()) {
                    String[] matches = new String[matcher.groupCount()];
                    for (int i = 0; i < matcher.groupCount(); i++) {
                        matches[i] = matcher.group(i + 1);
                    }
                    return String.join("-", matches);
                }
                return value;
            }
        };
    }

    @NotNull
    @Override
    public Parser<String> getParser(@NotNull IsbnFormat annotation, @NotNull Class<?> fieldType) {
        return new Parser<String>() {

            @NotNull
            @Override
            public String parse(@NotNull String text, @NotNull Locale locale) throws ParseException {
                return text.replace("-", "");
            }
        };
    }

}


