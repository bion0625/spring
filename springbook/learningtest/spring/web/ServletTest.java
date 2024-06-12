package springbook.learningtest.spring.web;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletConfig;
import org.springframework.web.servlet.ModelAndView;
import springbook.learningtest.spring.web.hello.HelloController;
import springbook.temp.HelloSpring;

import javax.servlet.ServletException;
import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ServletTest {
    @Test
    public void mockServletTest() throws ServletException, IOException {
        MockHttpServletRequest req = new MockHttpServletRequest("GET", "/hello");
        req.addParameter("name", "Spring");

        MockHttpServletResponse res = new MockHttpServletResponse();

        SimpleGetServlet servlet = new SimpleGetServlet();
        servlet.service(req, res);

        assertThat(res.getContentAsString().contains("Hello Spring"), is(true));
    }

    @Test
    public void dispatcherServletTest() throws ServletException, IOException {
        ConfigurableDispatcherServlet servlet = new ConfigurableDispatcherServlet();

        servlet.setRelativeLocations(getClass(), "hello/spring-servlet.xml");

        servlet.setClasses(HelloSpring.class);

        servlet.init(new MockServletConfig("spring"));

        MockHttpServletRequest req = new MockHttpServletRequest("GET", "/hello");
        req.addParameter("name", "Spring");
        MockHttpServletResponse res = new MockHttpServletResponse();

        servlet.service(req, res);

        ModelAndView mav = servlet.getModelAndView();
        assertThat(mav.getViewName(), is("/WEB-INF/view/hello.jsp"));
        assertThat((String)mav.getModel().get("message"), is("Hello Spring"));
    }

    @Test
    public void controllerTest() {
        ApplicationContext ac = new GenericXmlApplicationContext(
                "springbook/learningtest/spring/web/hello/spring-servlet.xml",
                "springbook/webapp/WEB-INF/applicationContext.xml"
        );
        HelloController helloController = ac.getBean(HelloController.class);

        MockHttpServletRequest req = new MockHttpServletRequest("GET", "/hello");
        req.addParameter("name", "Spring");
        MockHttpServletResponse res = new MockHttpServletResponse();

        ModelAndView mav = helloController.handleRequest(req, res);

        assertThat(mav.getViewName(), is("/WEB-INF/view/hello.jsp"));
        assertThat((String)mav.getModel().get("message"), is("Hello Spring"));
    }
}
