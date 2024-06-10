package springbook.learningtest.spring.web.customController;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component("/hello")
public class HelloController implements SimpleController {
    @ViewName("/WEB-INF/view/hello.jsp")
    @RequiredParams({"name"})
    @Override
    public void control(Map<String, String> params, Map<String, Object> model) {
        model.put("message", "Hello " + params.get("name"));
    }
}
