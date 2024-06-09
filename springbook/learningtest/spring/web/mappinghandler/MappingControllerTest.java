package springbook.learningtest.spring.web.mappinghandler;

import org.junit.Test;
import org.springframework.web.servlet.mvc.support.ControllerBeanNameHandlerMapping;
import org.springframework.web.servlet.mvc.support.ControllerClassNameHandlerMapping;
import springbook.learningtest.spring.web.AbstractDispatcherServletTest;

import javax.servlet.ServletException;
import java.io.IOException;

public class MappingControllerTest extends AbstractDispatcherServletTest {

    @Test
    public void beanNameUrlHanlderMappingTest() throws ServletException, IOException {
        setRelativeLocations("beanNameUrlHanlderMappingTest-context.xml")
                .runService("/s")
                .assertModel("testKey", "testValue")
                .assertViewName("testViewName");

        setRelativeLocations("beanNameUrlHanlderMappingTest-context.xml")
                .runService("/sl")
                .assertModel("testKey", "testValue")
                .assertViewName("testViewName");

        setRelativeLocations("beanNameUrlHanlderMappingTest-context.xml")
                .runService("/sabcd")
                .assertModel("testKey", "testValue")
                .assertViewName("testViewName");

        setRelativeLocations("beanNameUrlHanlderMappingTest-context.xml")
                .runService("/root/sub")
                .assertModel("testKey", "testValue")
                .assertViewName("testViewName");

        setRelativeLocations("beanNameUrlHanlderMappingTest-context.xml")
                .runService("/root/a/sub")
                .assertModel("testKey", "testValue")
                .assertViewName("testViewName");

        setRelativeLocations("beanNameUrlHanlderMappingTest-context.xml")
                .runService("/root/a/b/c/d/sub")
                .assertModel("testKey", "testValue")
                .assertViewName("testViewName");
    }

    @Test
    public void controllerBeanNameHandlerMappingTest() throws ServletException, IOException {
        setClasses(ControllerBeanNameHandlerMapping.class, MyController.class);
        runService("/hello")
                .assertModel("testKey", "testValue")
                .assertViewName("testViewName");
    }

    @Test
    public void controllerBeanNameHandlerMappingTestByUrlPrefixInXml() throws ServletException, IOException {
        setClasses(MyController.class)
                .setRelativeLocations("controllerBeanNameHandlerMappingTest-context.xml")
                .runService("/app/sub/hello")
                .assertModel("testKey", "testValue")
                .assertViewName("testViewName");
    }

    @Test
    public void controllerClassNameHandlerMappingTest() throws ServletException, IOException {
        setClasses(ControllerClassNameHandlerMapping.class, MyController.class)
                .runService("/my")
                .assertModel("testKey", "testValue")
                .assertViewName("testViewName");
    }

    @Test
    public void simpleUrlHandlerMappingTest() throws ServletException, IOException {
        setRelativeLocations("simpleUrlHandlerMappingTest-context.xml")
                .runService("/my")
                .assertModel("testKey", "testValue")
                .assertViewName("testViewName");

        setRelativeLocations("simpleUrlHandlerMappingTest-context.xml")
                .initRequest("/hello")
                .addParameter("name", "Spring")
                .runService()
                .assertModel("message", "Hello Spring")
                .assertViewName("/WEB-INF/view/hello.jsp");
    }
}
