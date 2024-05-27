package springbook.learningtest.spring.ioc.xml.property;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import springbook.learningtest.spring.ioc.xml.property.bean.Hello;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class RegisterBeanByXmlTest {
    @Autowired
    Hello hello;

    @Test
    public void propertyTagTest() {
        assertThat(hello.getName(), is("Spring"));
        assertThat(hello.getAge(), is(30));
        assertThat(hello.getMyClass().getClass().getName(), is("java.lang.String"));
    }
}
