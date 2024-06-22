package springbook.learningtest.spring.web.formTag;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import springbook.learningtest.spring.web.atmvc.JSR303.JSR303User;

import javax.validation.Valid;

@Controller
@SessionAttributes("user")
public class formTagController {

    @RequestMapping(value = "/form/user/add", method = RequestMethod.GET)
    public void addForm(Model model) {
        JSR303User user = new JSR303User();
        model.addAttribute("user", user);
    }

    @RequestMapping(value = "/form/user/add", method = RequestMethod.POST)
    public String add(@ModelAttribute("user") @Valid JSR303User user, BindingResult result) {
        System.out.println("---------------");
        System.out.println("Name: " + user.getName());
        System.out.println("Age: " + user.getAge());
        System.out.println("---------------");
        if (result.hasErrors()) return "form/user/add";
        else return "success";
    }
}
