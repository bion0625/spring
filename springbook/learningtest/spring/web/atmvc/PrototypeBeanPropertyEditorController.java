package springbook.learningtest.spring.web.atmvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.inject.Inject;
import javax.inject.Provider;
import java.util.List;

@Controller
@SessionAttributes("userTypes")
public class PrototypeBeanPropertyEditorController {

    @Autowired
    CodeService codeService;

    @Inject
    Provider<CodePropertyEditor> codePropertyEditor;

    @InitBinder
    public void initBiner(WebDataBinder binder) {
//        binder.registerCustomEditor(Code.class, new FakeCodePropertyEditor());

        // provider를 이용해 프로토타입 빈의 새 오브젝트를 가져온다.
        binder.registerCustomEditor(Code.class, codePropertyEditor.get());
    }

    @ModelAttribute("userTypes")
    public List<Code> userTypes() {
        return this.codeService.findCodes();
    }

    @RequestMapping("/add")
    public void add(@ModelAttribute User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            System.out.println("error!!!!");
        }
        System.out.println("--------------------------");
        System.out.println(user.getId());
        System.out.println(user.getName());
        System.out.println(user.getUserType());
        System.out.println("--------------------------");
    }

    @RequestMapping("/userType")
    public void form(@ModelAttribute("userTypes") List<Code> userTypes, Model model) {
        model.addAttribute(userTypes);
    }
}
