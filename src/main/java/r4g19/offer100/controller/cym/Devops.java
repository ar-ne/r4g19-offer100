package r4g19.offer100.controller.cym;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import r4g19.offer100.controller.ControllerBase;

@Controller
@RequestMapping("devops")
public class Devops extends ControllerBase {
    @GetMapping("")
    public String index(Model model) {
        model.addAttribute("userType", "dev");
        return "redirect:/devops/index";
    }

    @GetMapping("{page}")
    public String router(@PathVariable String page, Model model) {
        model.addAttribute("userType", "dev");
        return "devops/" + page;
    }
}
