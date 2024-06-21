package springbook.learningtest.spring.web.atmvc.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import springbook.user.domain.User;

public class UserValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        /*if (user.getName() == null | user.getName().length() == 0)
            errors.rejectValue("name", "field.required");*/
        ValidationUtils.rejectIfEmpty(errors, "name", "field.required");
        if (user.getAge() < 0)
            errors.rejectValue("name", "field.min", new Object[] {0}, null);
    }
}
