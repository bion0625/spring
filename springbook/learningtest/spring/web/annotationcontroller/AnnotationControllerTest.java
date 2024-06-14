package springbook.learningtest.spring.web.annotationcontroller;

import org.junit.Test;
import springbook.learningtest.spring.web.AbstractDispatcherServletTest;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import java.io.IOException;

public class AnnotationControllerTest extends AbstractDispatcherServletTest {
    @Test
    public void helloMethodTest() throws ServletException, IOException {
        setClasses(AnnotationController.class)
                .runService("/hello.html")
                .assertViewName("hello");
    }

    @Test
    public void complexMethodTest() throws ServletException, IOException {
        setClasses(AnnotationController.class)
                .initRequest("/complex")
                .addParameter("name", "Spring");
        setCookie("auth", "testAuth")
                .runService()
                .assertViewName("myview")
                .assertModel("info", "Spring/testAuth");
    }

    public AnnotationControllerTest setCookie(String name, String value) {
        if (this.request == null)
            throw new IllegalStateException("request가 초기화되지 않았습니다.");
        this.request.setCookies(new Cookie(name, value));
        return this;
    }
}
