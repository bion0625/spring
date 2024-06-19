package springbook.learningtest.spring.web.atmvc;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CodeService {
    public List<Code> findCodes();

    public Code getCode(int value);
}
