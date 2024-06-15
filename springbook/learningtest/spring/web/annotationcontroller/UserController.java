package springbook.learningtest.spring.web.annotationcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import springbook.user.domain.Level;
import springbook.user.domain.User;
import springbook.user.service.UserService;

@RequestMapping
public class UserController {

    @Autowired
    UserService userService;

    public UserController() {}

    @RequestMapping(value = "/user/add", method = RequestMethod.POST)
    public String add(@ModelAttribute User user) {
        user.setLevel(Level.BASIC);
        user.setLogin(0);
        user.setRecommend(0);
        userService.add(user);
        return "success";
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String getUsers(Model model) {
        model.addAttribute("users", userService.getAll());
        return "success";
    }

    @RequestMapping("/user/delete")
    public String deleteAll() {
        userService.deleteAll();
        return "success";
    }

    @RequestMapping("/user/update")
    public String update(@ModelAttribute("currentuser") User user) {
        user.setLevel(Level.BASIC);
        user.setLogin(0);
        user.setRecommend(0);
        userService.update(user);
        return "success";
    }
}
