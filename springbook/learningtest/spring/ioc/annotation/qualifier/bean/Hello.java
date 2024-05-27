package springbook.learningtest.spring.ioc.annotation.qualifier.bean;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class Hello {

//    @Autowired // 한 개의 생성자에만 부여할 수 있다.
//    public Hello(Printer printer) {
//        this.printer = printer;
//    }

    String name;

//    @Autowired
    Printer printer;

    /*
    * 스트링으로 된 빈 아이디가 키가 된다.
    * Printer 타입의 빈 오브젝트가 값이 된다.
    * */

    // 프로퍼티로 DI 받은 이름을 이용해 간단한 인사문구 만들기
    public String sayHello() {
        return "Hello " + name;
    }

    /*
    * DI에 의해 의존 오브젝트로 제공받은 Printer 타입의 오브젝트에게 출력 작업을 위임한다.
    * 구체적으로 어떤 방식으로 출력하는지는 상관하지 않는다.
    * 또한 어떤 방식으로 출력하도록 변경해도 Hello의 코드는 수정할 필요가 없다.
    * */
    public void print() {
        this.printer.print(sayHello());
    }

    public void setName (String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

//    @Autowired
    public void setPrinter(Printer printer) {
        this.printer = printer;
    }

    public Printer getPrinter() {
        return this.printer;
    }

    @Autowired // 한 개 이상의 설정용 메소드에 부여할 수 있다.
//    @Qualifier("sprinter")
    public void config(@Qualifier("sprinter") Printer printer) {
        this.printer = printer;
    }
}
