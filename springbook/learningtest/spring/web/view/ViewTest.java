package springbook.learningtest.spring.web.view;

import org.junit.Test;
import springbook.learningtest.spring.web.AbstractDispatcherServletTest;
import springbook.temp.HelloSpring;

import javax.servlet.ServletException;
import java.io.IOException;

public class ViewTest extends AbstractDispatcherServletTest {

    @Test
    public void internalResourceViewTest() throws ServletException, IOException {
        setClasses(HelloSpring.class, HelloController.class)
                .initRequest("/hello").addParameter("name", "Spring").runService()
                .assertModel("message", "Hello Spring");
    }
}
