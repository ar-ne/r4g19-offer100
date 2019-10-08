package r4g19.offer100.controller.yhr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import r4g19.offer100.controller.ControllerBase;
import r4g19.offer100.service.cym.CommonCRUD;

@Controller
public class Admin extends ControllerBase {
    @Autowired
    CommonCRUD commonCRUD;

    @GetMapping("admin")
    public String index() {
        return "redirect:/admin/index";
    }

    @GetMapping("/admin/{page}")
    public String router(@PathVariable String page) {
        return String.format("admin/%s", page);
    }

    //@GetMapping("")
}
