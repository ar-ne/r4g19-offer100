package r4g19.offer100.controller.yhr;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import r4g19.offer100.controller.ControllerBase;

@Controller
public class Admin extends ControllerBase {
    @GetMapping("admin")
    public String index() {
        return "redirect:/admin/index";
    }

    @GetMapping("/admin/{page}")
    public String router(@PathVariable String page) {
        return String.format("admin/%s", page);
    }
}
