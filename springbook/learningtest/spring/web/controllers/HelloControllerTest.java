package springbook.learningtest.spring.web.controllers;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class HelloControllerTest {

    @Test
    public void helloControllerTest() throws Exception {
        Map<String, String> params = new HashMap<>();
        params.put("name", "Spring");
        Map<String, Object> model = new HashMap<>();

        new HelloController().control(params, model);

        assertThat((String)model.get("message"), is("Hello Spring"));
    }
}
