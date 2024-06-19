package springbook.learningtest.spring.web.atmvc;

import org.junit.Test;
import org.springframework.beans.propertyeditors.CharsetEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.context.request.WebRequest;
import springbook.learningtest.spring.web.AbstractDispatcherServletTest;
import springbook.user.domain.Level;

import javax.servlet.ServletException;
import java.beans.PropertyEditorSupport;
import java.io.IOException;
import java.nio.charset.Charset;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class PropertyEditorTest extends AbstractDispatcherServletTest {
    @Test
    public void defaultPropertyEditorTest() throws ServletException, IOException {
        setClasses(DeaultPropertyEditorController.class)
                .initRequest("/hello").addParameter("charset", "UTF-8")
                .runService()
                .assertViewName("success");
        assertThat(((Charset)this.getModelAndView().getModel().get("UTF_8")).name(), is("UTF-8"));
    }

    @Controller
    static class DeaultPropertyEditorController {
        @RequestMapping("/hello")
        public String hello(@RequestParam Charset charset, Model model) {
            model.addAttribute(charset);
            return "success";
        }
    }

    @Test
    public void charsetEditor() {
        CharsetEditor charsetEditor = new CharsetEditor();
        charsetEditor.setAsText("UTF-8");
        assertThat(charsetEditor.getValue(), is(instanceOf(Charset.class)));
        assertThat((Charset)charsetEditor.getValue(), is(Charset.forName("UTF-8")));
    }

    @Test
    public void LevelPropertyEditorTest() {
        LevelPropertyEditor levelEditor = new LevelPropertyEditor();
        levelEditor.setAsText("3");
        assertThat((Level)levelEditor.getValue(), is(Level.GOLD));

        levelEditor.setValue(Level.BASIC);
        assertThat(levelEditor.getAsText(), is("1"));
    }

    static class LevelPropertyEditor extends PropertyEditorSupport {
        // setValue()에 의해 저장된 Level 타입 오브젝트를 가져와서 값을 문자로 변환한다.
        @Override
        public String getAsText() {
            return String.valueOf(((Level)this.getValue()).intValue());
        }

        // 파라미터로 제공된 문자열을 Level의 스태틱 메소드를 이용해 문자열로 변환한 뒤에 저장한다.
        @Override
        public void setAsText(String text) throws IllegalArgumentException {
            super.setValue(Level.valueOf(Integer.parseInt(text.trim())));
        }
    }

    @Test
    public void webDataBinderTest() {
        WebDataBinder dataBinder = new WebDataBinder(null);
        dataBinder.registerCustomEditor(Level.class, new LevelPropertyEditor());
        assertThat(dataBinder.convertIfNecessary("1", Level.class), is(Level.BASIC));
    }

    @Test
    public void levelTest() throws ServletException, IOException {
        setClasses(PropertyEditorController.class)
                .initRequest("/user/search").addParameter("level", "1")
                .runService()
                .assertViewName("success");
        assertThat((Level)this.getModelAndView().getModel().get("level"), is(Level.BASIC));
    }

    @Controller
    static class PropertyEditorController {
        @InitBinder
        public void initBinder(WebDataBinder dataBinder) {
            dataBinder.registerCustomEditor(Level.class, new LevelPropertyEditor());
        }

        @RequestMapping("/user/search")
        public String search(@RequestParam Level level, Model model) {
            model.addAttribute(level);
            return "success";
        }
    }

    static class MinMaxPropertyEditor extends PropertyEditorSupport {
        int min;
        int max;

        public MinMaxPropertyEditor(int min, int max) {
            this.min = min;
            this.max = max;
        }

        @Override
        public String getAsText() {
            return String.valueOf((Integer)this.getValue());
        }

        @Override
        public void setAsText(String text) throws IllegalArgumentException {
            Integer val = Integer.parseInt(text);
            // 최대값, 최소값과 비교해서 범위를 넘었으면 강제로 값을 수정해준다.
            if (val < min) val = min;
            else if (val > max) val = max;
            setValue(val);
        }
    }

    static class Member {
        public Member() {}
        public Member(int id, int age) {
            this.id = id;
            this.age = age;
        }
        int id;
        int age;
        public void setId(int id) {
            this.id = id;
        }
        public int getId() {
            return this.id;
        }
        public void setAge(int age) {
            this.age = age;
        }
        public int getAge() {
            return this.age;
        }
    }

    @Test
    public void MinMaxPropertyEditorTest() throws ServletException, IOException {
        setClasses(MinMaxPropertyEditorController.class)
                .initRequest("/add")
                .addParameter("id", "1000")
                .addParameter("age", "1000")
                .runService()
                .assertViewName("addSuccess");
        Member member = (Member) this.getModelAndView().getModel().get("member");
        assertThat(member.getId(), is(1000));
        assertThat(member.getAge(), is(200));
    }

    @Controller
    static class MinMaxPropertyEditorController {
        @InitBinder
        public void initBinder(WebDataBinder dataBinder) {
            dataBinder.registerCustomEditor(int.class, "age",
                    new MinMaxPropertyEditor(0, 200));
        }

        @RequestMapping("/add")
        public String add(@ModelAttribute Member member, Model model) {
            model.addAttribute(member);
            return "addSuccess";
        }
    }

    static class MyWebBindingInitializer implements WebBindingInitializer {
        @Override
        public void initBinder(WebDataBinder binder, WebRequest request) {
            binder.registerCustomEditor(Level.class, new LevelPropertyEditor());
        }
    }

    @Test
    public void MyWebBindingInitializerTest() throws ServletException, IOException {
        setRelativeLocations("webBindingInitializer-context.xml")
                .setClasses(MyWebBindingInitializerController.class)
                .initRequest("/user/search")
                .addParameter("level", "1")
                .runService()
                .assertViewName("searchView");
        assertThat((Level)this.getModelAndView().getModel().get("level"), is(Level.BASIC));
    }

    @Controller
    static class MyWebBindingInitializerController {
        @RequestMapping("/user/search")
        public String search(@RequestParam Level level, Model model) {
            model.addAttribute(level);
            return "searchView";
        }
    }
}
