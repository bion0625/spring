package springbook.learningtest.spring.ioc.annotation.configurationBean;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import springbook.learningtest.spring.ioc.annotation.configurationBean.bean.Hello;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class RegisterBeanByAnnotationTest {
    @Autowired
    Hello hello;

    @Test
    public void autowiredTest() {
        assertThat(hello.getName(), is(nullValue()));
        assertThat(hello.getPrinter().getClass().getName(), is("springbook.learningtest.spring.ioc.annotation.configurationBean.bean.StringPrinter"));
    }
}
