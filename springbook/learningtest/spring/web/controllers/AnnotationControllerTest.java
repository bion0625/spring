package springbook.learningtest.spring.web.controllers;

import org.junit.Test;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter;
import org.springframework.web.servlet.mvc.annotation.DefaultAnnotationHandlerMapping;
import springbook.learningtest.spring.web.AbstractDispatcherServletTest;

import javax.servlet.ServletException;
import java.io.IOException;

public class AnnotationControllerTest extends AbstractDispatcherServletTest {

    @Test
    public void annotationControllerTest() throws ServletException, IOException {
        setClasses(AnnotationMethodHandlerAdapter.class, DefaultAnnotationHandlerMapping.class, HelloController.class);
        initRequest("/hello").addParameter("name", "Spring")
                .runService()
                .assertModel("message", "Hello Spring")
                .assertViewName("WEB-INF/view/hello.jsp");
    }

    @Controller
    static class HelloController {
        @RequestMapping("/hello")
        public String hello(@RequestParam("name") String name, ModelMap map) {
            map.put("message", "Hello " + name);
            return "WEB-INF/view/hello.jsp";
        }
    }
}