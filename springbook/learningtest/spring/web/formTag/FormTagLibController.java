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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
@SessionAttributes("user")
public class FormTagLibController {

    @ModelAttribute("types")
    public List<Map.Entry<String, String>> types() {
        Map<String, String> map = new HashMap<>();
        map.put("관리자", "1");
        map.put("회원", "2");
        map.put("손님", "3");
        return new ArrayList<>(map.entrySet());
    }

    @ModelAttribute("interests")
    public Set<String> interests() {
        return new HashSet<>(Arrays.asList("Python","Java","C#","Ruby"));
    }

    @RequestMapping(value = "/lib/form/user/add", method = RequestMethod.GET)
    public void addForm(Model model) {
        JSR303User user = new JSR303User();
//        user.setType("3");
        model.addAttribute("user", user);
    }

    @RequestMapping(value = "/lib/form/user/add", method = RequestMethod.POST)
    public String add(@ModelAttribute("user") @Valid JSR303User user, BindingResult result) {
        System.out.println("-------------");
        System.out.println("name: " + user.getName());
        System.out.println("age: " + user.getAge());
        Set<String> interests = user.getInterests();
        if (interests != null) {
            for (String interest : interests) {
                System.out.println("interest: " + interest);
            }
        }
        System.out.println("type: " + user.getType());
        System.out.println("-------------");
        if (result.hasErrors()) {
            return "lib/form/user/add";
        }
        else return "success";
    }
}
