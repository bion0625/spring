package springbook.learningtest.spring.web.atmvc;

import org.junit.Before;
import org.junit.Test;
import springbook.user.domain.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

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
        initRequest("/user/add", "GET").runService()
                .assertViewName("user/add");
        HttpSession session = this.request.getSession();

        initRequest("/user/add", "POST")
                .addParameter("id", "bumjin")
                .addParameter("password", "p1")
                .addParameter("name", "박범진")
                .addParameter("email", "test01@test.com");
        this.request.setSession(session);
        runService()
                .assertViewName("user/editSuccess");

        runService("/users");
        assertModelByListSize("users", 1);
    }

    @Test
    public void editTest() throws ServletException, IOException {
        initRequest("/user/add", "GET").runService()
                .assertViewName("user/add");
        HttpSession session = this.request.getSession();

        initRequest("/user/add", "POST")
                .addParameter("id", "bumjin")
                .addParameter("password", "p1")
                .addParameter("name", "박범진")
                .addParameter("email", "test01@test.com");
        this.request.setSession(session);
        runService()
                .assertViewName("user/editSuccess");

        runService("/users");
        assertModelByListSize("users", 1);
        assertThat(((List<User>)this.getModelAndView().getModel().get("users")).get(0).getEmail(), is("test01@test.com"));

        initRequest("/user/edit", "GET").addParameter("id", "bumjin")
                .runService()
                .assertViewName("user/edit");
        session = this.request.getSession();
        assertThat(session.getAttribute("currentUser"),
                is(this.getModelAndView().getModel().get("currentUser")));

        initRequest("/user/edit", "POST")
                .addParameter("password", "p1")
                .addParameter("name", "박범진")
                .addParameter("email", "test03@test.com");
        this.request.setSession(session);
        runService()
                .assertViewName("user/editSuccess");

        assertThat(this.request.getSession().getAttribute("currentUser"), is(nullValue()));

        runService("/users");
        assertModelByListSize("users", 1);
        assertThat(((List<User>)this.getModelAndView().getModel().get("users")).get(0).getEmail(), is("test03@test.com"));
    }
}
