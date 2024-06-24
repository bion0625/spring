package springbook.learningtest.spring.web.ajax;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import springbook.user.domain.User;

@Controller
public class AjaxController {

    @RequestMapping(value = "/ajax", method = RequestMethod.GET)
    public void ajaxForm() {}

    @RequestMapping(value = "/logincheckid/{loginId}", method = RequestMethod.GET)
    @ResponseBody
    public Result loginCheck(@PathVariable("loginId") String loginId) {
        System.out.println("loginId: " + loginId);
        return new Result(true, loginId + (int)(Math.random()*1000));
    }

    @RequestMapping(value = "/user/register", method = RequestMethod.POST)
    @ResponseBody
    public User register(@RequestBody User user) {
        System.out.println("--------------");
        System.out.println("Id :" + user.getId());
        System.out.println("Password :" + user.getPassword());
        System.out.println("Name :" + user.getName());
        System.out.println("--------------");
        return user;
    }
}
