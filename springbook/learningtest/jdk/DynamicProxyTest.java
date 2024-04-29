package springbook.learningtest.jdk;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.junit.Test;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;

public class DynamicProxyTest {
    @Test
    public void invokeMethod() throws Exception {
        String name = "String";

        // length()
        assertThat(name.length(), is(6));

        Method lengtMethod = name.getClass().getMethod("length");
        assertThat((Integer)lengtMethod.invoke(name), is(6));

        // charAt()
        assertThat(name.charAt(0), is('S'));

        Method charAtMethod = name.getClass().getMethod("charAt", int.class);
        assertThat((Character)charAtMethod.invoke(name, 0), is('S'));
    }

    public class HelloUppercase implements Hello {
        Hello hello; // 위임할 타깃 오브젝트. 여기서는 타깃 클래스의 오브젝트인 것은 알지만 다른 프록시를 추가할 수도 있으므로 인터페이스로 접근한다.

        public HelloUppercase(Hello hello) {
            this.hello = hello;
        }

        @Override
        public String sayHello(String name) {
            return this.hello.sayHello(name).toUpperCase();
        }
        @Override
        public String sayHi(String name) {
            return this.hello.sayHi(name).toUpperCase();
        }
        @Override
        public String sayThankYou(String name) {
            return this.hello.sayThankYou(name).toUpperCase();
        }
    }

    @Test
    public void simpleProxy() {
        // JDK 다이내믹 프록시 생성
        Hello proxiedHello = (Hello)Proxy.newProxyInstance(
            getClass().getClassLoader(), 
            new Class[] { Hello.class }, 
            new UpperCaseHandler(new HelloTarget()));
        assertThat(proxiedHello.sayHello("TOBY"), is("HELLO TOBY"));
        assertThat(proxiedHello.sayHi("TOBY"), is("HI TOBY"));
        assertThat(proxiedHello.sayThankYou("TOBY"), is("THANK YOU TOBY"));
    }

    public class UpperCaseHandler implements InvocationHandler {
        // 다이나믹 프록시로부터 전달받은 요청을 다시 타깃 오브젝트에 위임해야 하기 때문에 오브젝트를 주입받아둔다.
        Object target; // 어떤 종류의 인터페이스를 구현한 타깃에도 적용 가능하도록 Object 타입으로 수정
        public UpperCaseHandler(Object target) {
            this.target = target;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            // 타깃으로 위임. 인터페이스의 메소드 호출에 모두 적용된다.
            Object ret = method.invoke(target, args);
            // 리턴 타입과 메소드 이름이 일치하는 경우에만 부가기능을 적용한다.
            if (ret instanceof String && method.getName().startsWith("say")) {
                return ((String)ret).toUpperCase(); // 부가기능 제공
            }
            else {
                return ret;
            }
        }
    }

    @Test
    public void dynamicProxy() {
        ProxyFactoryBean pfBean = new ProxyFactoryBean();
        pfBean.setTarget(new HelloTarget()); // 타깃 설정
        pfBean.addAdvice(new UpperCaseAdvice()); // 부가기능을 담은 어드바이스를 추가한다. 여러 개를 추가할 수도 있다.

        Hello proxiedHello = (Hello) pfBean.getObject(); // FactoryBean이므로 getObject()로 생성된 프록시를 가져온다.

        assertThat(proxiedHello.sayHello("TOBY"), is("HELLO TOBY"));
        assertThat(proxiedHello.sayHi("TOBY"), is("HI TOBY"));
        assertThat(proxiedHello.sayThankYou("TOBY"), is("THANK YOU TOBY"));
    }

    static class UpperCaseAdvice implements MethodInterceptor{
        @Override
        public Object invoke(MethodInvocation invocation) throws Throwable {
            // 리플렉션의 Method와 달리 메소드 실팽 시 타깃 오브젝트를 전달할 필요가 없다.
            // MethodInvocation은 메소드 정보와 함께 타깃 오브젝트를 알고 있기 때문이다.
            String ret = (String)invocation.proceed(); // 부가기능 적용
            return ret.toUpperCase();
        }
    }

    interface Hello { // 타깃과 프록시가 구현할 인터페이스
        String sayHello(String name);
        String sayHi(String name);
        String sayThankYou(String name);
    }

    public class HelloTarget implements Hello { // 타깃 클래스
        @Override
        public String sayHello(String name) { return "Hello " + name; }
        @Override
        public String sayHi(String name) { return "Hi " + name; }
        @Override
        public String sayThankYou(String name) { return "Thank You " + name; }
    }

    @Test
    public void pointcutAdvisor() {
        ProxyFactoryBean pfBean = new ProxyFactoryBean();
        pfBean.setTarget(new HelloTarget());

        // 메소드 이름을 비교해서 대상을 선정하는 알고리즘을 제공하는 포인트컷 생성
        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        // 이름 비교조건 설정. sayH로 시작하는 모든 메소드를 선택하게 한다.
        pointcut.setMappedName("sayH*");

        // 포인트컷과 어드바이스를 Advisor로 묶어서 한 번에 추가
        pfBean.addAdvisor(new DefaultPointcutAdvisor(pointcut, new UpperCaseAdvice()));

        Hello proxiHello = (Hello) pfBean.getObject();

        assertThat(proxiHello.sayHello("Toby"), is("HELLO TOBY"));
        assertThat(proxiHello.sayHi("Toby"), is("HI TOBY"));
        // 메소드 이름이 포인트컷의 선정조건에 맞지 않으므로, 부가기능(대문자변환)이 적용되지 않는다.
        assertThat(proxiHello.sayThankYou("Toby"), is("Thank You Toby"));
    }
}
