package springbook.learningtest.spring.web.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.view.InternalResourceView;
import springbook.temp.HelloSpring;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Component("/hello")
public class HelloController implements Controller {

    @Autowired
    HelloSpring helloSpring;
    @Override
    public ModelAndView handleRequest(HttpServletRequest req, HttpServletResponse res) throws Exception {
        String name = req.getParameter("name");

        String message = this.helloSpring.sayHello(name);

        // 모델 생성
        Map<String, Object> model = new HashMap<>();
        model.put("message", message);

        // 뷰 생성
        InternalResourceView view = new InternalResourceView("WEB-INF/view/hello.jsp");
        return new ModelAndView(view, model);
    }
}
