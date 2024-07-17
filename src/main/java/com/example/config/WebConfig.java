package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatterForFieldType(LocalDateTime.class, new LocalDateTimeFormatter());
    }

    @Bean
    public LocalDateTimeFormatter localDateTimeFormatter() {
        return new LocalDateTimeFormatter();
    }

    public static class LocalDateTimeFormatter implements org.springframework.format.Formatter<LocalDateTime> {

        @Override
        public LocalDateTime parse(String text, java.util.Locale locale) {
            return LocalDateTime.parse(text, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        }

        @Override
        public String print(LocalDateTime object, java.util.Locale locale) {
            return DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(object);
        }
    }
}

