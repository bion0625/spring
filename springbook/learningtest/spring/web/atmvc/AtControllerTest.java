package springbook.learningtest.spring.web.atmvc;

import org.junit.Test;

import javax.servlet.ServletException;
import java.io.IOException;

public class AtControllerTest extends AbstractAnnotationControllerTest {
    @Test
    public void helloMethodTest() throws ServletException, IOException {
        setClasses(AtController.class)
                .runService("/hello.html")
                .assertViewName("hello");
    }

    @Test
    public void complexMethodTest() throws ServletException, IOException {
        setClasses(AtController.class)
                .initRequest("/complex")
                .addParameter("name", "Spring");
        setCookie("auth", "testAuth")
                .runService()
                .assertViewName("myview")
                .assertModel("info", "Spring/testAuth");
    }

    @Test
    public void pathVariableTest() throws ServletException, IOException {
        setClasses(AtController.class)
                .runService("/user/view/10")
                .assertViewName("view_10");

        setClasses(AtController.class)
                .runService("/member/double/order/101")
                .assertViewName("lookup/double/101");
    }

    @Test
    public void requestParamTest() throws ServletException, IOException {
        setClasses(AtController.class)
                .initRequest("/param")
                    .addParameter("id", "11")
                    .addParameter("name", "Spring")
                .runService()
                .assertViewName("param/11/Spring");
    }

    @Test
    public void requestParamMapTest() throws ServletException, IOException {
        setClasses(AtController.class)
                .initRequest("/param/map")
                .addParameter("test", "k")
                .addParameter("test1", "again")
                .runService()
                .assertViewName("/k/again");
    }

    @Test
    public void requestParamRequiredTest() throws ServletException, IOException {
        setClasses(AtController.class)
                .runService("/param/required")
                .assertViewName("paramByRequired_0");

        setClasses(AtController.class)
                .initRequest("/param/required").addParameter("id", "5")
                .runService()
                .assertViewName("paramByRequired_5");
    }

    @Test
    public void simpleReqeustParamTest() throws ServletException, IOException {
        setClasses(AtController.class)
                .initRequest("/param/simple").addParameter("id", "7")
                .runService()
                .assertViewName("paramBySimple_7");
    }

    @Test
    public void simpleReqeustParamThanTest() throws ServletException, IOException {
        setClasses(AtController.class)
                .initRequest("/param/simple/than").addParameter("id", "7")
                .runService()
                .assertViewName("paramBySimpleThan_7");
    }

    @Test
    public void cookieTest() throws ServletException, IOException {
        setClasses(AtController.class)
                .initRequest("/cookie");
        setCookie("auth", "authCookie")
                .runService()
                .assertViewName("cookie_authCookie");
    }

    @Test
    public void simpleCookieTest() throws ServletException, IOException {
        setClasses(AtController.class)
                .initRequest("/cookie/simple");
        setCookie("auth", "authCookie")
                .runService()
                .assertViewName("cookieBySimple_authCookie");
    }

    @Test
    public void cookieCheckTest() throws ServletException, IOException {
        setClasses(AtController.class)
                .initRequest("/cookie/check")
                .runService()
                .assertViewName("cookieCheck_default");

        setCookie("auth", "authCookie")
                .runService()
                .assertViewName("cookieCheck_authCookie");
    }

    @Test
    public void headerTest() throws ServletException, IOException {
        setClasses(AtController.class)
                .runService("/header")
                .assertViewName("header/defaultHost/default");

        addHeader("Host", "sampleHost").addHeader("Keep-Alive", "sampleKeepAlive")
                .runService()
                .assertViewName("header/sampleHost/sampleKeepAlive");
    }

    @Test
    public void simpleHeaderTest() throws ServletException, IOException {
        setClasses(AtController.class)
                .initRequest("/header/simple");
        addHeader("Host", "sampleHost").addHeader("Keep-Alive", "sampleKeepAlive")
                .runService()
                .assertViewName("headerBySimple/sampleHost/sampleKeepAlive");
    }

    @Test
    public void modelAddAttributeTest() throws ServletException, IOException {
        setClasses(AtController.class)
                .runService("/addAttribute");
        assertModelByString("user", "bumjin").assertViewName("addAttributeView");
    }

    @Test
    public void modelAttributeTest() throws ServletException, IOException {
        setClasses(AtController.class)
                .initRequest("/user/search")
                .addParameter("id", "1")
                .addParameter("name", "Spring")
                .addParameter("level", "3")
                .addParameter("email", "test@test.com")
                .runService()
                .assertViewName("search/1/Spring/3/test@test.com");
    }

    @Test
    public void modelAttributeByNoAnnotationTest() throws ServletException, IOException {
        setClasses(AtController.class)
                .initRequest("/user/search/noAnnotation")
                .addParameter("id", "1")
                .addParameter("name", "Spring")
                .addParameter("level", "3")
                .addParameter("email", "test@test.com")
                .runService()
                .assertViewName("search/1/Spring/3/test@test.com");
    }

    @Test
    public void bindingResultTest() throws ServletException, IOException {
        setClasses(AtController.class)
                .initRequest("/bindingResult")
                .addParameter("id", "1")
                .runService()
                .assertViewName("search/1");
        addParameter("id", "abcd")
                .runService()
                .assertViewName("search/bindingResult");
    }

    @Test
    public void errorsTest() throws ServletException, IOException {
        setClasses(AtController.class)
                .initRequest("/errors")
                .addParameter("id", "1")
                .runService()
                .assertViewName("search/1");
        addParameter("id", "abcd")
                .runService()
                .assertViewName("search/errors");
    }

    @Test
    public void modelAttributeMethodTest() throws ServletException, IOException {
        setClasses(AtController.class);
        runService("/modelAttributeMethod/A")
                .assertModel("code", "codeString")
                .assertViewName("view/a");
        runService("/modelAttributeMethod/B")
                .assertModel("code", "codeString")
                .assertViewName("view/b");
    }

    @Test
    public void modelAndViewReturnTest() throws ServletException, IOException {
        setClasses(AtController.class)
                .initRequest("/ModelAndView/hello")
                .addParameter("name", "Spring")
                .runService()
                .assertViewName("/view/hello")
                .assertModel("name", "Spring");
    }

    @Test
    public void stringReturnTest() throws ServletException, IOException {
        setClasses(AtController.class)
                .initRequest("/String/hello")
                .addParameter("name", "Spring")
                .runService()
                .assertViewName("/view/hello")
                .assertModel("name", "Spring");
    }

    @Test
    public void noResponseBodyTest() throws ServletException, IOException {
        setClasses(AtController.class)
                .runService("/no/responsebody")
                .assertViewName("<html><body>Hello Spring</body></html>")
                .getContentAsString();
        assertContentAsString("");
    }

    @Test
    public void responseBodyTest() throws ServletException, IOException {
        setClasses(AtController.class).runService("/responsebody");
        assertContentAsString("<html><body>Hello Spring</body></html>");
    }
}
