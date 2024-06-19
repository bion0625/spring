package springbook.learningtest.spring.web.atmvc.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import springbook.user.domain.User;

@Controller
@SessionAttributes("user")
public class ConverterController {
//    @Autowired
//    ConversionService conversionService;
//
//    @InitBinder
//    public void initBinder(WebDataBinder binder) {
//        binder.setConversionService(this.conversionService);
//    }

    @RequestMapping("/converter")
    public void converter(@ModelAttribute User user) {
        System.out.println("-----------------------");
        System.out.println(user.getId());
        System.out.println(user.getName());
        System.out.println(user.getLevel());
        System.out.println("-----------------------");
    }

    @RequestMapping("/converterForm")
    public void converterForm(Model model) {
        User user = new User();
        model.addAttribute(user);
    }
}
