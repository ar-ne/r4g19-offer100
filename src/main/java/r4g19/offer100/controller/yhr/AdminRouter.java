package r4g19.offer100.controller.yhr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import r4g19.offer100.controller.ControllerBase;
import r4g19.offer100.service.cym.CommonCRUD;
import r4g19.offer100.service.yhr.AdminService;

import javax.annotation.Resource;

@Controller
@RequestMapping("admin")
public class AdminRouter extends ControllerBase {
    @Autowired
    CommonCRUD commonCRUD;
    @Resource
    AdminService adminService;

    /***
     * 用户列表
     * @param model
     * @return
     */
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("userType","admin");
        return "redirect:userlist";
    }


    /***
     * 公司列表
     * @param model
     * @return
     */
    @GetMapping("/enterprise")
    public String enterpriseList(Model model) {
        model.addAttribute("userType","admin");
        return "redirect:enterpriseList";
    }


/*    *//***
     * ajax接收username删除公司信息
     * @param usrename
     *//*
    @ResponseBody
    @RequestMapping("/deleteEnterprise")
    public void deleteUser(String usrename){


    }*/


    @GetMapping("{page}")
    public String router(Model model ,@PathVariable String page) {
        model.addAttribute("userType","admin");
        return String.format("admin/%s", page);
    }

    //@GetMapping("")
}
