package springbook.learningtest.spring.web.atmvc.converter;

import org.springframework.core.convert.converter.Converter;
import springbook.user.domain.Level;

public class StringToLevelConverter implements Converter<String, Level> {
    @Override
    public Level convert(String text) {
        return Level.valueOf(Integer.parseInt(text));
    }
}
