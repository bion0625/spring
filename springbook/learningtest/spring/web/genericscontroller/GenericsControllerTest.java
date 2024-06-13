package springbook.learningtest.spring.web.genericscontroller;

import org.junit.Test;
import springbook.learningtest.spring.web.AbstractDispatcherServletTest;

import javax.servlet.ServletException;
import java.io.IOException;

public class GenericsControllerTest extends AbstractDispatcherServletTest {
    @Test
    public void genericsControllerTest() throws ServletException, IOException {
        setClasses(UserController.class);
        runService("/user/add").assertViewName("add.jsp");
        runService("/user/update").assertViewName("update.jsp");
        runService("/user/view").assertViewName("view.jsp");
        runService("/user/delete").assertViewName("delete.jsp");
        runService("/user/login").assertViewName("login.jsp");
    }
}
