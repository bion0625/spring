package springbook.learningtest.spring.web.atmvc.webdatabinder;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import springbook.user.domain.User;

@Controller
@SessionAttributes("user")
public class WebDataBinderController {
//    @InitBinder
//    public void initBinder(WebDataBinder dataBinder) {
//        dataBinder.setAllowedFields("name", "email");
//        dataBinder.setRequiredFields("email"); // 이건 명시만 할 뿐 검증은 작동하지 않는 걸로 보임
//    }

    @RequestMapping(value = "/webDataBinder", method = RequestMethod.GET)
    public void WebDataBinderForm(Model model) {
        User user = new User();
        user.setAutoLogin(true);
        user.setType("member");
        model.addAttribute(user);
    }

    @RequestMapping(value = "/webDataBinder", method = RequestMethod.POST)
    public void WebDataBinder(@ModelAttribute User user, Errors errors) {
        if (errors.hasErrors()) {
            errors.getFieldError();
        }
        System.out.println("-----------------");
        System.out.println("getId: " + user.getId());
        System.out.println("getName: " + user.getName());
        System.out.println("getPassword: " + user.getPassword());
        System.out.println("getEmail: " + user.getEmail());
        System.out.println("getLevel: " + user.getLevel());
        System.out.println("getLogin: " + user.getLogin());
        System.out.println("getRecommend: " + user.getRecommend());
        System.out.println("getAutoLogin: " + user.getAutoLogin());
        System.out.println("getAutoLogin: " + user.getType());
        System.out.println("-----------------");
    }
}
