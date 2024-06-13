package springbook.learningtest.spring.web.genericscontroller;

import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

public abstract class GenericsController<T, K, S> {
    S s;

    @RequestMapping("/add") public String add(T entity) {return "add.jsp";}
    @RequestMapping("/update") public String update(T entity) {return "update.jsp";}
    @RequestMapping("/view") public String view(K id) {return "view.jsp";}
    @RequestMapping("/delete") public String delete(K id) {return "delete.jsp";}
    @RequestMapping("/list") public List<T> list() {return null;}
}
