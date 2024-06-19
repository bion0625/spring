package springbook.learningtest.spring.web.atmvc;

import java.util.ArrayList;
import java.util.List;

public class CodeServiceImpl implements CodeService {
    @Override
    public List<Code> findCodes() {
        List<Code> userTypes = new ArrayList<>();
        userTypes.add(Code.ADMIN);
        userTypes.add(Code.USER);
        userTypes.add(Code.GUEST);
        return userTypes;
    }

    @Override
    public Code getCode(int value) {
        switch (String.valueOf(value)) {
            case "1": return Code.ADMIN;
            case "2": return Code.USER;
            case "3": return Code.GUEST;
        }
        return null;
    }
}
