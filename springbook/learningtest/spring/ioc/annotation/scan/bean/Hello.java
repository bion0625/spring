package springbook.learningtest.spring.ioc.annotation.scan.bean;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Hello {
    String name;

    @Autowired
    Printer printer;

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

    /*
    * <property name="printer" ref="printer"/>와 동일한 의존관계 메타정보로 변환된다.
    * */
    public void setPrinter(Printer printer) {
        this.printer = printer;
    }

    public Printer getPrinter() {
        return this.printer;
    }
}
