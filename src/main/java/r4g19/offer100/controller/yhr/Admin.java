package r4g19.offer100.controller.yhr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import r4g19.offer100.api.cym.User;
import r4g19.offer100.controller.ControllerBase;
import r4g19.offer100.service.cym.CommonCRUD;

import java.util.List;

@Controller
@RequestMapping("admin")
public class Admin extends ControllerBase {
    @Autowired
    CommonCRUD commonCRUD;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("userType","admin");
        return "redirect:index";
    }

    @GetMapping("/enterprise")
    public String enterpriseManage(Model model) {
        model.addAttribute("userType","admin");
        return "redirect:enterpriseManage";
    }

    @GetMapping("/noticeManage")
    public String notice(Model model) {
        model.addAttribute("userType","admin");
        return "redirect:notice";
    }
    //
    //@GetMapping("/userList")
    //public List<User> userList(){
    //
    //}

    @GetMapping("{page}")
    public String router(Model model ,@PathVariable String page) {
        model.addAttribute("userType","admin");return String.format("admin/%s", page);
    }

    //@GetMapping("")
}
