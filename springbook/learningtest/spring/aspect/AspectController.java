package springbook.learningtest.spring.aspect;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("user")
public class AspectController {

    @RequestMapping(value = "/aspect/user/add", method = RequestMethod.GET)
    public String addUserForm(Model model) {
        User user = new User();
        user.setLogin(0);
        user.setRecommend(0);
        model.addAttribute("user", user);
        return "aspect";
    }

    @RequestMapping(value = "/aspect/user/add", method = RequestMethod.POST)
    public String addUser(@ModelAttribute User user) {
        System.out.println("---------------");
        System.out.println(user.getId());
        System.out.println(user.getName());
        System.out.println(user.getLevel());
        user.add();
        System.out.println("---------------");
        return "aspect";
    }
}
