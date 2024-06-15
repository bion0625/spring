package springbook.learningtest.spring.web.annotationcontroller;

import org.junit.Before;
import org.junit.Test;

import javax.servlet.ServletException;
import java.io.IOException;

public class UserControllerTest extends AbstractAnnotationControllerTest {

    @Before
    public void setUp() throws ServletException, IOException {
        setLocations("test-applicationContext.xml")
                .setClasses(UserController.class)
                .runService("/user/delete")
                .assertViewName("success");
        assertModelByListSize("users", 0);
    }

    @Test
    public void addTest() throws ServletException, IOException {
        initRequest("/user/add", "POST")
                .addParameter("id", "bumjin")
                .addParameter("password", "p1")
                .addParameter("name", "박범진")
                .addParameter("email", "test01@test.com")
                .runService()
                .assertViewName("success");

        runService("/users");
        assertModelByListSize("users", 1);
    }

    @Test
    public void updateTest() throws ServletException, IOException {
        initRequest("/user/add", "POST")
                .addParameter("id", "bumjin")
                .addParameter("password", "p1")
                .addParameter("name", "박범진")
                .addParameter("email", "test01@test.com")
                .runService()
                .assertViewName("success");

        runService("/users");
        assertModelByListSize("users", 1);

        initRequest("/user/update")
                .addParameter("id", "bumjin")
                .addParameter("password", "p1")
                .addParameter("name", "박범진")
                .addParameter("email", "test03@test.com")
                .runService()
                .assertViewName("success");
        assertModelByString("currentuser", "bumjin");
    }
}
