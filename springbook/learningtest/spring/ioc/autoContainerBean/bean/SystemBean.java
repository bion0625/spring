package springbook.learningtest.spring.ioc.autoContainerBean.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import javax.annotation.Resource;

public class SystemBean {
    /*
    * 타입에 의한 자동와이어링을 통해 애플리케이션 컨텍스트를 직접 DI 받는다.
    * @Resource를 사용해도 된다.
    * */
    @Autowired
    ApplicationContext contextByAutowired;

    @Resource
    ApplicationContext contextByResource;

//    @Value("#{systemProperties['os.name']}")
//    private String osName;
//
//    @Value("#{systemEnvironment['Path']}")
//    private String path;
//
//    public void setOsName(String osName) {
//        this.osName = osName;
//    }
//
//    public String getOsName() {
//        return this.osName;
//    }
//
//    public void setPath(String path) {
//        this.path = path;
//    }
//
//    public String getPath() {
//        return this.path;
//    }

    public String getHelloNameByAutowired() {
        // 애플리케이션 컨텍스트를 직접 사용하는 코드를 작성할 수 있다.
        Hello hello = contextByAutowired.getBean("hello", Hello.class);
        return hello.getName();
    }

    public String getHelloNameByResource() {
        Hello hello = contextByResource.getBean("hello", Hello.class);
        return hello.getName();
    }
}
