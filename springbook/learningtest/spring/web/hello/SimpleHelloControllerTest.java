package springbook.learningtest.spring.web.hello;

import org.junit.Test;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import springbook.learningtest.spring.web.AbstractDispatcherServletTest;
import springbook.temp.HelloSpring;

import javax.servlet.ServletException;
import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;


public class SimpleHelloControllerTest extends AbstractDispatcherServletTest {

    @Test
    public void helloController() throws ServletException, IOException {
        /*
        * 컨텍스트를 위한 설정파일과 빈 클래스 등록
        * */
        ModelAndView mav = setRelativeLocations("spring-servlet.xml")
                .setClasses(HelloSpring.class)
                /*
                 * 요청(request)정보 설정과 파라미터 추가
                 * */
                .initRequest("/hello", RequestMethod.GET)
                .addParameter("name", "Spring")
                .runService()
                .assertViewName("/WEB-INF/view/hello.jsp")
                .assertModel("message", "Hello Spring")
                .getModelAndView();

        assertThat(mav.getViewName(), is("/WEB-INF/view/hello.jsp"));
        assertThat((String)mav.getModel().get("message"), is("Hello Spring"));
    }
}
