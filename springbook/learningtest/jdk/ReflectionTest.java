package springbook.learningtest.jdk;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.junit.Test;

public class ReflectionTest {
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

    interface Hello {
        String sayHello(String name);
        String sayHi(String name);
        String sayThankYou(String name);
    }

    public class HelloTarget implements Hello {
        @Override
        public String sayHello(String name) {
            return "Hello " + name;
        }
        @Override
        public String sayHi(String name) {
            return "Hi " + name;
        }
        @Override
        public String sayThankYou(String name) {
            return "Thank You " + name;
        }
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
        Hello hello = new HelloTarget();
        assertThat(hello.sayHello("Toby"), is("Hello Toby"));
        assertThat(hello.sayHi("Toby"), is("Hi Toby"));
        assertThat(hello.sayThankYou("Toby"), is("Thank You Toby"));

        Hello proxyHello = new HelloUppercase(new HelloTarget()); // 프록시를 통해 타깃 오브젝트에 접근하도록 구성한다.
        assertThat(proxyHello.sayHello("TOBY"), is("HELLO TOBY"));
        assertThat(proxyHello.sayHi("TOBY"), is("HI TOBY"));
        assertThat(proxyHello.sayThankYou("TOBY"), is("THANK YOU TOBY"));
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
        // 생성된 다이나믹 프록시 오브젝트는 Hello 인터페이스를 구현하고 있으므로 Hello 타입으로 캐스팅해도 안전하다.
        Hello proxyHello = (Hello)Proxy.newProxyInstance(
            getClass().getClassLoader(), // 동적으로 생성되는 다이나믹 프록시 클래스의 로딩에 사용할 클래스 로더
            new Class[] { Hello.class }, // 구현할 인터페이스
            new UpperCaseHandler(new HelloTarget()) // 부가기능과 위임 코드를 담은 InvocationHandler
        );

        assertThat(proxyHello.sayHello("TOBY"), is("HELLO TOBY"));
        assertThat(proxyHello.sayHi("TOBY"), is("HI TOBY"));
        assertThat(proxyHello.sayThankYou("TOBY"), is("THANK YOU TOBY"));
    }
}
