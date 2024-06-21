package springbook.learningtest.spring.web.atmvc.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import springbook.user.domain.User;

import javax.validation.Valid;

@Controller
@SessionAttributes("user")
public class ValidatorController {

    @Autowired UserValidator validator;

    @RequestMapping(value = "/validator", method = RequestMethod.GET)
    public void validatorForm(Model model) {
        User user = new User();
        model.addAttribute(user);
    }

    @RequestMapping(value = "/controller", method = RequestMethod.POST)
    public String validator(@ModelAttribute User user, BindingResult bindingResult) {
        this.validator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            System.out.println("controller error!!!!!");
            return "validator";
        } else return "success";
    }

    @InitBinder
    public void initBinde(WebDataBinder dataBinder) {
        dataBinder.setValidator(this.validator);
    }

    @RequestMapping("/atValid")
    public String atValid(@ModelAttribute @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            System.out.println("atValid error!!!!!!");
            return "validator";
        } else return "success";
    }
}
