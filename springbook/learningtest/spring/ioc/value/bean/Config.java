//package springbook.learningtest.spring.ioc.value.bean;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class Config {
//    @Value("${database.username}")
//    private String name;
//
//    @Value("true")
//    private Boolean flag;
//
//    @Value("1.2")
//    private double rate;
//
//    @Value("1,2,3,4")
//    private int[] intarr;
//
//    @Bean
//    public Hello hello() {
//        Hello hello = new Hello();
//        hello.setName(name);
//        hello.setFlag(flag);
//        hello.setRate(rate);
//        hello.setIntarr(intarr);
//        return hello;
//    }
//
//    @Bean
//    public Printer printer() {
//        return new StringPrinter();
//    }
//}
