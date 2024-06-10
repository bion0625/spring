package springbook.learningtest.spring.web.customController;

import org.junit.Test;
import springbook.learningtest.spring.web.AbstractDispatcherServletTest;

import javax.servlet.ServletException;
import java.io.IOException;

public class HandlerAdapterTest extends AbstractDispatcherServletTest {
    @Test
    public void simpleHandlerAdapterTest() throws ServletException, IOException {
        setClasses(SimpleHandlerAdapter.class, HelloController.class);
        initRequest("/hello").addParameter("name", "Spring").runService();
        assertViewName("/WEB-INF/view/hello.jsp");
        assertModel("message", "Hello Spring");
    }
}
