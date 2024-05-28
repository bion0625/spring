package springbook.learningtest.spring.ioc.value;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import springbook.learningtest.spring.ioc.value.bean.Hello;
import springbook.learningtest.spring.ioc.value.bean.Printer;

import java.util.*;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class PropertyTest {
    @Autowired
    Hello hello;

    @Autowired
    Printer printer;

    @Autowired
    Hello hello2;

    @Autowired
    Hello hello3;

    @Autowired
    Hello hello4;

    @Test
    public void propertyTagTest() {
        assertThat(hello.getName(), is("Everyone"));
        assertThat(hello.getFlag(), is(true));
        assertThat(hello.getRate(), is(1.2));
        assertThat(hello.getIntarr(), is(new int[] {1,2,3,4}));
        List<String> list = new ArrayList<>();
        list.add("Spring");
        list.add("Io");
        list.add("DI");
        assertThat(hello.getNames(), is(list));
        Map<String, Integer> map = new HashMap<>();
        map.put("Kim", 30);
        map.put("Lee", 35);
        map.put("Ahn", 40);
        assertThat(hello.getAges(), is(map));
        Properties props = new Properties();
        props.setProperty("username","Spring");
        props.setProperty("password","Book");
        assertThat(hello.getSetting(), is(props));
        assertThat(hello.getBeans().get(0), is(hello));
        assertThat(hello.getBeans().get(1), is(printer));

        assertThat(hello2.getNames().get(0), is("Spring2"));
        assertThat(hello2.getNames().get(1), is("Io2"));
        assertThat(hello2.getNames().get(2), is("DI2"));
        assertThat(hello2.getAges().get("Kim2"), is(30));
        assertThat(hello2.getAges().get("Lee2"), is(35));
        assertThat(hello2.getAges().get("Ahn2"), is(40));
        assertThat(hello2.getSetting().get("username2"), is("Spring2"));
        assertThat(hello2.getSetting().get("password2"), is("Book2"));

        assertThat(hello3.getNames(), is(nullValue()));
        assertThat(hello3.getName(), is(""));

        assertThat(hello4.getName(), is(hello.getName()));
    }
}
