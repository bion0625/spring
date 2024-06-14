package springbook.learningtest.spring.web.annotationcontroller;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping
public class AnnotationController {

    @RequestMapping("/hello.html")
    public void hello() {}

    @RequestMapping("/complex")
    public String complex(@RequestParam("name") String name,
                          @CookieValue("auth") String auth, ModelMap model) {
        model.put("info", name + "/" + auth);
        return "myview";
    }
}
