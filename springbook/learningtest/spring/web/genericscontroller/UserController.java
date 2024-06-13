package springbook.learningtest.spring.web.genericscontroller;

import org.springframework.web.bind.annotation.RequestMapping;
import springbook.user.domain.User;
import springbook.user.service.UserService;

@RequestMapping("/user")
public class UserController extends GenericsController<User, Integer, UserService> {
    @RequestMapping("/login")
    public String login(String userId, String password) {
        return "login.jsp";
    }
}
