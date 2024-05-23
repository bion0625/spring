package springbook.learningtest.spring.ioc;

import org.junit.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.PropertiesBeanDefinitionReader;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.context.support.StaticApplicationContext;
import springbook.learningtest.spring.ioc.bean.Hello;
import springbook.learningtest.spring.ioc.bean.StringPrinter;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class RegisterBeanTest {
    @Test
    public void registerBean() {
        // IoC 컨테이터 생성, 생성과 동시에 컨테이너로 동작한다.
        StaticApplicationContext ac = new StaticApplicationContext();
        // Hello 클래스를 hello1이라는 이름의 싱글톤 빈으로 컨테이너에 등록한다.
        ac.registerSingleton("hello1", Hello.class);

        // IoC 컨테이터가 등록한 빈을 생성했는지 확인하기 위해 빈을 요청하고 Null이 아닌지 확인한다.
        Hello hello1 = ac.getBean("hello1", Hello.class);
        assertThat(hello1, is(notNullValue()));

        /*
        * 빈 메타정보를 담은 오브젝트를 만든다.
        * 빈 클래스는 Hello로 지정한다.
        * <bean class="springbook.learningtest..Hello"/>에 해당하는 메타정보다.
        * */
        BeanDefinition helloDef = new RootBeanDefinition(Hello.class);
        /*
        * 빈의 name 프로퍼티에 들어갈 값을 지정한다.
        * <property name="name" value="Spring"/>에 해당한다.
        * */
        helloDef.getPropertyValues().addPropertyValue("name", "Spring");
        /*
        * 앞에서 생성한 빈 메타정보를 hello2라는 이름을 가진 빈으로 해서 등록한다.
        * <bean id="hello2" .. />에 해당한다.
        * */
        ac.registerBeanDefinition("hello2", helloDef);

        // BeanDefinition으로 등록된 빈이 컨테이너에 의해 만들어지고 프로퍼티 설정이 됐는지 확인한다.
        Hello hello2 = ac.getBean("hello2", Hello.class);
        assertThat(hello2.sayHello(), is("Hello Spring"));

        // 처음 등록한 빈과 두 번째 등록한 빈이 모두 동일한 Hello 클래스지만 별개의 오브젝트로 생성됐다.
        assertThat(hello1, is(not(hello2)));

        assertThat(ac.getBeanFactory().getBeanDefinitionCount(), is(2));
    }

    @Test
    public void registerBeanWithDependency() {
        StaticApplicationContext ac = new StaticApplicationContext();

        ac.registerBeanDefinition("printer",
                // StringPrinter 클래스 타입이며 printer라는 이름을 가진 빈을 등록한다.
                new RootBeanDefinition(StringPrinter.class));

        BeanDefinition helloDef = new RootBeanDefinition(Hello.class);
        // 단순 값을 갖는 프로퍼티 등록
        helloDef.getPropertyValues().addPropertyValue("name", "Spring");
        // 아이디가 printer인 빈에 대한 레퍼런스를 프로퍼티로 등록
        helloDef.getPropertyValues().addPropertyValue("printer", new RuntimeBeanReference("printer"));

        ac.registerBeanDefinition("hello", helloDef);

        Hello hello = ac.getBean("hello", Hello.class);
        hello.print();

        /*
        * Hello 클래스의 print() 메소드는 DI 된 Printer 타입의 오브젝트에게 요청해서 인삿말을 출력한다.
        * 이 결과를 스트링으로 저장해두는 printer 빈을 통해 확인한다.
        * */
        assertThat(ac.getBean("printer").toString(), is("Hello Spring"));
    }

    @Test
    public void genericApplicationContext() {
        GenericApplicationContext ac = new StaticApplicationContext();

        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(ac);
        reader.loadBeanDefinitions(
                // XmlBeanDefinitionReader는 기본적으로 클래스패스로 정의된 리소스로부터 파일을 읽는다.
                "springbook/learningtest/spring/ioc/genericApplicationContext.xml");

        // 모든 메타정보가 등록이 완료됐으니 애플리케이션 컨테이너를 초기화하라는 명령이다.
        ac.refresh();

        Hello hello = ac.getBean("hello", Hello.class);
        hello.print();

        assertThat(ac.getBean("printer").toString(), is("Hello Spring"));
    }

    @Test
    public void genericXmlApplicationContext() {
        // 애플리케이션 컨텍스트 생성과 동시에 XML 파일을 읽어오고 초기화까지 수행한다.
        GenericXmlApplicationContext ac = new GenericXmlApplicationContext(
                "springbook/learningtest/spring/ioc/genericApplicationContext.xml");

        // IoC 컨테이너가 구성한 빈 오브젝트로 이뤄진 애플리케이션을 기동할 오브젝트를 getBean()으로 가져온다.
        Hello hello = ac.getBean("hello", Hello.class);
        hello.print();

        assertThat(ac.getBean("printer").toString(), is("Hello Spring"));
    }

    @Test
    public void genericApplicationContextWithProperties() {
        GenericApplicationContext ac = new StaticApplicationContext();

        PropertiesBeanDefinitionReader reader = new PropertiesBeanDefinitionReader(ac);
        reader.loadBeanDefinitions("springbook/learningtest/spring/ioc/genericApplicationContext.properties");

        ac.refresh();

        Hello hello = ac.getBean("hello", Hello.class);
        hello.print();

        assertThat(ac.getBean("printer").toString(), is("Hello Spring"));
    }
}
