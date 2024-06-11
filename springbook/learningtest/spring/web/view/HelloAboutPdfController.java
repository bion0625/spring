package springbook.learningtest.spring.web.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import springbook.temp.HelloSpring;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class HelloAboutPdfController implements Controller {
    @Autowired
    HelloPdfView helloPdfView;

    @Autowired
    HelloSpring helloSpring;

    @Override
    public ModelAndView handleRequest(HttpServletRequest req, HttpServletResponse res) throws Exception {
        Map<String, Object> model = new HashMap<>();
        model.put("message", helloSpring.sayHello(req.getParameter("name")));

        return new ModelAndView(this.helloPdfView, model);
    }
}
