package springbook.learningtest.spring.web.customController;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class SimpleHandlerAdapter implements HandlerAdapter {

    /*
    * 핸들러 어댑터가 지원하는 타입을 확인해준다.
    * 하나 이상의 타입을 지원하게 할 수도 있다.
    * */
    @Override
    public boolean supports(Object handler) {
        return (handler instanceof SimpleController);
    }

    /*
    * getLastModified()는 controller의 getLastModified() 메소드를 다시 호출해서 컨트롤러가 결정하도록 만든다.
    * 캐싱을 적용하지 않으려면 0보다 작은 값을 리턴한다.
    * getLastModified()의 지원은 없는 것으로 만들었다.
    * */
    @Override
    public long getLastModified(HttpServletRequest httpServletRequest, Object handler) {
        return -1;
    }

    @Override
    public ModelAndView handle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {
        Method m = ReflectionUtils.findMethod(handler.getClass(), "control", Map.class, Map.class);
        /*
        * 컨트롤러 메소드의 어노테이션에서 필요한 정보를 가져온다.
        * 스프링 유틸리티 클래스를 이용해 간단히 애노테이션을 가져왔다.
        * */
        ViewName viewName = AnnotationUtils.getAnnotation(m, ViewName.class);
        RequiredParams requiredParams = AnnotationUtils.getAnnotation(m, RequiredParams.class);

        HashMap<String, String> params = new HashMap<>();
        // 애노테이션 requiredParams의 value 앨리먼트 값을 사용한다.
        for (String param : requiredParams.value()) {
            String value = req.getParameter(param);
            if (value == null) throw new IllegalStateException();
            params.put(param, value);
        }

        HashMap<String, Object> model = new HashMap<>();

        /*
        * DispatcherServlet은 컨트롤러의 타입을 모르기 때문에 컨트롤러를 Object 타입으로 넘겨준다.
        * 이를 적절한 컨트롤러 타입으로 캐스팅해서 메소드를 호출해준다.
        * */
        ((SimpleController)handler).control(params, model);

        return new ModelAndView(viewName.value(), model);
    }
}
