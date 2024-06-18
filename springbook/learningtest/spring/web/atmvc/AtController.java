package springbook.learningtest.spring.web.atmvc;

import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import springbook.user.domain.Level;
import springbook.user.domain.User;

import java.util.Map;

import static springbook.user.service.UserServiceImpl.MIN_LOGCOUNT_FOR_SILVER;

@RequestMapping
public class AtController {

    @RequestMapping("/hello.html")
    public void helloHtml() {}

    @RequestMapping("/complex")
    public String complex(@RequestParam("name") String name,
                          @CookieValue("auth") String auth, ModelMap model) {
        model.put("info", name + "/" + auth);
        return "myview";
    }

    @RequestMapping("/user/view/{id}")
    public String view(@PathVariable("id") int id) {
        return "view_" + id;
    }

    @RequestMapping("/member/{membercode}/order/{orderid}")
    public String lookup(@PathVariable("membercode") String code,
                         @PathVariable("orderid") int orderid) {
        return String.format("lookup/%s/%d",code, orderid);
    }

    @RequestMapping("/param")
    public String param(@RequestParam("id") int id, @RequestParam("name") String name) {
        return String.format("param/%d/%s", id, name);
    }

    @RequestMapping("/param/map")
    public String paramByMap(@RequestParam Map<String, String> map) {
        StringBuilder result = new StringBuilder();
        for (String value : map.values()) {
            result.append("/").append(value);
        }
        return result.toString();
    }

    @RequestMapping("/param/required")
    public String paramByRequired(@RequestParam(value = "id", required = false, defaultValue = "0") int id) {
        return String.format("paramByRequired_%d", id);
    }

    @RequestMapping("/param/simple")
    public String paramBySimple(@RequestParam int id) {
        return String.format("paramBySimple_%d", id);
    }

    @RequestMapping("/param/simple/than")
    public String paramBySimpleThan(int id) {
        return String.format("paramBySimpleThan_%d", id);
    }

    @RequestMapping("/cookie")
    public String cookie(@CookieValue("auth") String auth) {
        return String.format("cookie_%s", auth);
    }

    @RequestMapping("/cookie/simple")
    public String cookieBySimple(@CookieValue String auth) {
        return String.format("cookieBySimple_%s", auth);
    }

    @RequestMapping("/cookie/check")
    public String cookieCheck(@CookieValue(value = "auth", required = false, defaultValue = "default") String auth) {
        return String.format("cookieCheck_%s", auth);
    }

    @RequestMapping("/header")
    public String header(@RequestHeader(value = "Host", required = false, defaultValue = "defaultHost") String host,
                         @RequestHeader(value = "Keep-Alive", required = false, defaultValue = "default") String keepAlive) {
        return String.format("header/%s/%s", host, keepAlive);
    }

    @RequestMapping("/header/simple")
    public String headerBySimple(@RequestHeader String host, @RequestHeader(value = "Keep-Alive", required = false, defaultValue = "default") String keepAlive) {
        return String.format("headerBySimple/%s/%s", host, keepAlive);
    }

    @RequestMapping("/addAttribute")
    public String addAttribute(Model model) {
        User user = new User("bumjin", "박범진", "p1", "test01@test.com", Level.BASIC, MIN_LOGCOUNT_FOR_SILVER-1, 0);
        model.addAttribute(user);
        return "addAttributeView";
    }

    @RequestMapping("/user/search")
    public String search(@ModelAttribute UserSearch userSearch) {
        return String.format("search/%d/%s/%d/%s",
                userSearch.getId(), userSearch.getName(), userSearch.getLevel(), userSearch.getEmail());
    }

    @RequestMapping("/user/search/noAnnotation")
    public String searchByNoAnnotation(UserSearch userSearch) {
        return String.format("search/%d/%s/%d/%s",
                userSearch.getId(), userSearch.getName(), userSearch.getLevel(), userSearch.getEmail());
    }

    @RequestMapping("/bindingResult")
    public String bindingResult(UserSearch userSearch, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return String.format("search/bindingResult");
        else return String.format("search/%d", userSearch.getId());
    }

    @RequestMapping("/errors")
    public String errors(UserSearch userSearch, Errors errors) {
        if (errors.hasErrors()) return String.format("search/errors");
        else return String.format("search/%d", userSearch.getId());
    }

    @ModelAttribute("code")
    public String code() {
        return "codeString";
    }

    @RequestMapping("//modelAttributeMethod/A")
    public String modelAttributeMethodA() {
        return "view/a";
    }

    @RequestMapping("//modelAttributeMethod/B")
    public String modelAttributeMethodB() {
        return "view/b";
    }

    @RequestMapping("/ModelAndView/hello")
    public ModelAndView modelAndViewHello(@RequestParam("name") String name, Model model) {
        model.addAttribute("name", name);
        return new ModelAndView("/view/hello");
    }

    @RequestMapping("/String/hello")
    public String StringHello(String name, Model model) {
        model.addAttribute("name", name);
        return "/view/hello";
    }

    @RequestMapping("/no/responsebody")
    public String noResponseBody() {
        return "<html><body>Hello Spring</body></html>";
    }

    @RequestMapping("/responsebody")
    @ResponseBody
    public String responseBody() {
        return "<html><body>Hello Spring</body></html>";
    }
}
