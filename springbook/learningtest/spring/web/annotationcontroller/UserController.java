package springbook.learningtest.spring.web.annotationcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import springbook.user.domain.Level;
import springbook.user.domain.User;
import springbook.user.service.UserService;

@RequestMapping
@SessionAttributes("user")
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

    @RequestMapping(value = "/user/edit", method = RequestMethod.GET)
    public String form(@RequestParam String id, Model model) {
        model.addAttribute("user", userService.get(id));
        return "user/edit";
    }

    @RequestMapping(value = "/user/edit", method = RequestMethod.POST)
    public String submit(@ModelAttribute User user, SessionStatus sessionStatus) {
        this.userService.update(user);
        // 현재 컨트롤러에 의해 세션에 저장된 정보를 모두 제거해준다.
        sessionStatus.setComplete();
        return "user/editSuccess";
    }
}
