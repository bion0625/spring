package springbook.temp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class HelloController implements Controller {
    // 부모 컨텍스트인 루트 컨텍스트로부터 HelloSpring 빈을 DI 받을 수 있다.
    @Autowired
    HelloSpring helloSpring;

    @Override
    public ModelAndView handleRequest(HttpServletRequest req, HttpServletResponse res) {
        // 1. 사용자 요청 해석
        String name = req.getParameter("name");

        // 2. 비즈니스 로직 호출
        String message = this.helloSpring.sayHello(name);

        Map<String, Object> model = new HashMap<>();
        // 3. 모델 정보 생성
        model.put("message", message);

        // 4. 뷰 지정
        return new ModelAndView("/WEB-INF/view/hello.jsp", model);
    }
}
