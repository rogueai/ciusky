package dev.rogueai.collection.config;

import dev.rogueai.collection.controller.validator.IsbnFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    @Autowired
    IsbnFormatter isbnFormatter;

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatterForFieldAnnotation(isbnFormatter);
    }
}
