package springbook.learningtest.spring.web.atmvc;

import springbook.learningtest.spring.web.AbstractDispatcherServletTest;

import javax.servlet.http.Cookie;
import java.io.UnsupportedEncodingException;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class AbstractAnnotationControllerTest extends AbstractDispatcherServletTest {

    public AbstractDispatcherServletTest setCookie(String name, String value) {
        if (this.request == null)
            throw new IllegalStateException("request가 초기화되지 않았습니다.");
        this.request.setCookies(new Cookie(name, value));
        return this;
    }

    public AbstractAnnotationControllerTest addHeader(String name, String value) {
        if (this.request == null)
            throw new IllegalStateException("request가 초기화되지 않았습니다.");
        this.request.addHeader(name, value);
        return this;
    }

    public AbstractAnnotationControllerTest assertModelByString(String name, String value) {
        assertThat(this.getModelAndView().getModel().get(name).toString(), is(value));
        return this;
    }

    public <T>AbstractAnnotationControllerTest assertModelByListSize(String objectName, int userSize) {
        List<T> list = (List<T>) this.getModelAndView().getModel().get(objectName);
        if (list == null) {
            assertThat(0, is(userSize));
        } else assertThat(list.size(), is(userSize));
        return this;
    }

    public AbstractAnnotationControllerTest assertContentAsString(String value) throws UnsupportedEncodingException {
        assertThat(this.getContentAsString(), is(value));
        return this;
    }
}
