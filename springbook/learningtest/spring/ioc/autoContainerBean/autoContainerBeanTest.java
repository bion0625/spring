package springbook.learningtest.spring.ioc.autoContainerBean;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import springbook.learningtest.spring.ioc.autoContainerBean.bean.Hello;
import springbook.learningtest.spring.ioc.autoContainerBean.bean.SystemBean;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class autoContainerBeanTest {

    @Autowired
    Hello hello;

    @Autowired
    SystemBean systemBean;

    @Test
    public void systemBean() {
        assertThat(hello.getName(), is("autoContainerBeanName"));
        assertThat(systemBean.getHelloNameByAutowired(), is(hello.getName()));
        assertThat(systemBean.getHelloNameByResource(), is(hello.getName()));
//        assertThat(systemBean.getOsName(), is("Windows 10"));
    }
}
