package springbook.learningtest.spring.ioc.annotation.configurationBean.bean;


import org.springframework.beans.factory.annotation.Autowired;

//@Component
public class Hello {

    String name;

    @Autowired Printer printer;

    public String sayHello() {
        return "Hello " + name;
    }

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

}
