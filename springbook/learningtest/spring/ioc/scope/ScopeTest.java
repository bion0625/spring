package springbook.learningtest.spring.ioc.scope;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ScopeTest {

    @Test
    public void simgletonScope() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(
                SingletonBean.class, SingletonClientBean.class);
        // Set은 중복을 허용하지 않으므로 같은 오브젝트는 여러 번 추가해도 한 개만 남는다.
        Set<SingletonBean> beans = new HashSet<>();

        // DL에서 싱글톤 확인
        beans.add(ac.getBean(SingletonBean.class));
        beans.add(ac.getBean(SingletonBean.class));
        assertThat(beans.size(), is(1));

        // DI에서 싱글톤 확인
        beans.add(ac.getBean(SingletonClientBean.class).bean1);
        beans.add(ac.getBean(SingletonClientBean.class).bean2);
        assertThat(beans.size(), is(1));

    }

    /*
    * 싱글톤 스코프 빈.
    * Scope 빈 메타정보의 디폴트 값은 'singleton'이기 때문에 별도의 스코프 설정은 필요 없다.
    * */
    static class SingletonBean {}

    static class SingletonClientBean {
        /*
        * 한 번 이상의 DI가 일어날 수 있도록 두 개의 DI용 프로퍼티를 선언해뒀다.
        * */
        @Autowired SingletonBean bean1;
        @Autowired SingletonBean bean2;
    }

    @Test
    public void prototypeScope() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(
                PrototypeBean.class, PrototypeClientBean.class);
        Set<PrototypeBean> bean = new HashSet<>();

        /*
        * 프로토타입 빈은 DL 방식으로 컨테이너에 빈을 요청할 때마다
        * 새로운 빈 오브젝트가 만들어지는 것을 확인한다.
        * */
        bean.add(ac.getBean(PrototypeBean.class));
        assertThat(bean.size(), is(1));
        bean.add(ac.getBean(PrototypeBean.class));
        assertThat(bean.size(), is(2));

        /*
        * 프로토타입 빈을 DI 할 때도
        * 주입받는 프로퍼티마다 다른 오브젝트가 만들어지는 것을 확인한다.
        * */
        bean.add(ac.getBean(PrototypeClientBean.class).bean1);
        assertThat(bean.size(), is(3));
        bean.add(ac.getBean(PrototypeClientBean.class).bean2);
        assertThat(bean.size(), is(4));
    }

    @Scope("prototype")
    static class PrototypeBean {}

    static class PrototypeClientBean {
        @Autowired PrototypeBean bean1;
        @Autowired PrototypeBean bean2;
    }
}
