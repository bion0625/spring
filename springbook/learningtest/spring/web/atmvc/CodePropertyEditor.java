package springbook.learningtest.spring.web.atmvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import java.beans.PropertyEditorSupport;

@Scope("prototype")
public class CodePropertyEditor extends PropertyEditorSupport {
    @Autowired
    CodeService codeService;

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        setValue(this.codeService.getCode(Integer.parseInt(text)));
    }

    @Override
    public String getAsText() {
        return String.valueOf(getValue());
    }
}
