package springbook.learningtest.spring.web.atmvc.fomatter;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.math.BigDecimal;
import java.util.Calendar;

@Controller
@SessionAttributes("product")
public class FormatterController {
    @RequestMapping(value = "/formatter", method = RequestMethod.GET)
    public void formatter(Model model) {
        Product product = new Product("write product name!", new BigDecimal(1000000), Calendar.getInstance());
        model.addAttribute(product);
    }

    @RequestMapping(value = "/formatter", method = RequestMethod.POST)
    public String formatter(@ModelAttribute Product product, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.getFieldError("birthday"));
        }
        System.out.println("---------------------");
        System.out.println(product.getName());
        System.out.println(product.getPrice());
        System.out.println(product.getBirthday().getTime());
        System.out.println("---------------------");
        return "success";
    }
}
