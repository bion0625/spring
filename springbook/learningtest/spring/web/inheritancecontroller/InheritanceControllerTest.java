package springbook.learningtest.spring.web.inheritancecontroller;

import org.junit.Test;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import springbook.learningtest.spring.web.AbstractDispatcherServletTest;

import javax.servlet.ServletException;
import java.io.IOException;

public class InheritanceControllerTest extends AbstractDispatcherServletTest {
    @Test
    public void extendsControllerTest() throws ServletException, IOException {
        setClasses(Sub.class)
                .runService("/user/list")
                .assertViewName("list.jsp");

        setClasses(SubWithOverride.class)
                .runService("/user/list")
                .assertViewName("list.jsp");
    }

    @RequestMapping("/user") static class Super {
        @RequestMapping("/list") public String list() {
            return "list.jsp";
        }
    }

    static class Sub extends Super {}

    static class SubWithOverride extends Super {
        @Override
        public String list() {
            return "notlist.jsp";
        }
    }

    @Test
    public void implementsControllerTest() throws ServletException, IOException {
        setClasses(Impl.class)
                .runService("/user/list")
                .assertViewName("implements.jsp");
    }

    @RequestMapping("/user") static interface Intf {
        @RequestMapping("/list") String list();
    }

    static class Impl implements Intf {
        @Override
        public String list() {
            return "implements.jsp";
        }
    }

    @Test
    public void extendsControllerWithMixAnnotationTest() throws ServletException, IOException {
        setClasses(SubWithRequestMappingMethod.class)
                .runService("/user/list")
                .assertViewName("list.jsp");
    }

    @RequestMapping("/user") static class SuperWithRequestMappingType { }

    static class SubWithRequestMappingMethod extends SuperWithRequestMappingType {
        @RequestMapping("/list") public String list() {
            return "list.jsp";
        }
    }

    @Test
    public void implementsControllerWithMixAnnotationTest() throws ServletException, IOException {
        setClasses(ImplWithRequestMappingMethod.class)
                .runService("/user/list")
                .assertViewName("list.jsp");
    }

    @RequestMapping("/user") static interface IntfWithRequestMappingType { public String list(); }

    static class ImplWithRequestMappingMethod implements IntfWithRequestMappingType {
        @RequestMapping("/list")
        @Override
        public String list() {
            return "list.jsp";
        }
    }

    @Test
    public void extendsControllerWithMixAnnotationReverseTest() throws ServletException, IOException {
        setClasses(SubWithRequestMappingType.class)
                .runService("/user/list")
                .assertViewName("list.jsp");

        setClasses(SubWithRequestMappingTypeWithOverride.class)
                .runService("/user/list")
                .assertViewName("list.jsp");
    }

    static class SuperWithRequestMappingMethod {
        @RequestMapping("/list") public String list() {
            return "list.jsp";
        }
    }

    @RequestMapping("/user") static class SubWithRequestMappingType extends SuperWithRequestMappingMethod { }

    @RequestMapping("/user") static class SubWithRequestMappingTypeWithOverride extends SuperWithRequestMappingMethod {
        @Override
        public String list() {
            return "notlist.jsp";
        }
    }

    @Test
    public void extendsControllerWithNewAnnotationTest() throws ServletException, IOException {
        setClasses(SubWithNewRequestMapping.class)
                .runService("/user/list")
                .assertViewName("newList.jsp");
    }

    @RequestMapping("/usr") static class SuperWithOldRequestMapping {
        @RequestMapping(value = "catalog", method = RequestMethod.POST)
        public String list() {
            return "list.jsp";
        }
    }

    @RequestMapping("/user") static class SubWithNewRequestMapping extends SuperWithOldRequestMapping {
        @Override @RequestMapping("/list") public String list() {
            return "newList.jsp";
        }
    }

    @Test
    public void implementsControllerWithNewAnnotationTest() throws ServletException, IOException {
        setClasses(impleWithNewRequestMapping.class)
                .runService("/user/list")
                .assertViewName("list.jsp");
    }

    @RequestMapping("/usr") static interface InftWithOldRequestMapping {
        @RequestMapping(value = "catalog", method = RequestMethod.POST)
        public String list();
    }

    @RequestMapping("/user") static class impleWithNewRequestMapping implements InftWithOldRequestMapping {
        @Override @RequestMapping("/list") public String list() {
            return "list.jsp";
        }
    }
}
