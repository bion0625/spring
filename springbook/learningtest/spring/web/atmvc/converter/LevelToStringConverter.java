package springbook.learningtest.spring.web.atmvc.converter;

import org.springframework.core.convert.converter.Converter;
import springbook.user.domain.Level;

public class LevelToStringConverter implements Converter<Level, String> {
    @Override
    public String convert(Level level) {
        return String.valueOf(level.intValue());
    }
}
