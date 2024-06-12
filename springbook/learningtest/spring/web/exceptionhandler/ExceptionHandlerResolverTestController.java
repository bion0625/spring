package springbook.learningtest.spring.web.exceptionhandler;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ExceptionHandlerResolverTestController {
    @RequestMapping("/exceptionHandlerTest")
    public void exceptionHandlerTest() {

        // DAO 작업 중 예외 발생 가능성이 있다.
        throw new RuntimeException("testExceptionMsg");
    }

//    @ExceptionHandler(RuntimeException.class) // 메소드가 처리할 예외 종류
//    public ModelAndView runtimeExceptionHandler(RuntimeException ex) {
//        // msg 모델 오브젝트와 함께 runtimeexception 뷰로 전환한다.
//        return new ModelAndView("runtimeexception").addObject("msg", ex.getMessage());
//    }

    @RequestMapping("/notInServiceExceptionTest")
    public void notInServiceException() {
        throw new NotInServiceException("notInServiceExceptionTest");
    }
}
