package springbook.learningtest.spring.ioc.annotation.configurationBean.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean
//    public Hello hello() {
    public Hello hello(Printer printer) {
        Hello hello = new Hello();
        // printer() 메소드를 실행해서 돌아온 오브젝트를 직접 수정자를 통해 DI한다. 일종의 수동 DI 방식이다.
//        hello.setPrinter(printer());
        /*
        * printer() 메소드에 의해 Printer 타입의 빈이 선언된 것이다.
        * 따라서 이를 hello() 메소드의 파라미터로 지정하면
        * 마치 @Autowired한 것과 동일하게 파라미터로 Printer 타입의 빈 정보가 제공된다.
        * */
        hello.setPrinter(printer);
        return hello;
    }

    @Bean
    public Printer printer() {
        return new StringPrinter();
    }
}
