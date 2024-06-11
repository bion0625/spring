package springbook.learningtest.spring.web.view;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.view.xml.MarshallingView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class HelloByMarshallController implements Controller {
    @Resource
    MarshallingView helloMarshallingView;

    @Override
    public ModelAndView handleRequest(HttpServletRequest req, HttpServletResponse res) throws Exception {
        Map<String, Object> model = new HashMap<>();
        model.put("info", new Info("Hello " + req.getParameter("name")));

        if ("xml".equals(req.getParameter("type"))) return new ModelAndView(helloMarshallingView, model);
        else return new ModelAndView("/hello", model);
    }
}
