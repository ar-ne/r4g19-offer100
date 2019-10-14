package r4g19.offer100.controller.cym;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import r4g19.offer100.controller.ControllerBase;
import r4g19.offer100.jooq.tables.pojos.Hiring;
import r4g19.offer100.service.cym.CommonCRUD;
import r4g19.offer100.service.cym.EntrepreneurialService;
import r4g19.offer100.service.cym.HiringService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;

import static r4g19.offer100.properties.cym.Vars.PUBLIC_PAGES;

/**
 * 本项目的神奇之处
 * 页面路由，将对应的页面，对应到相应的view
 */
@Controller
public class Router extends ControllerBase {
    @Autowired
    HiringService hiringService;
    @Autowired
    EntrepreneurialService entrepreneurialService;
    @Autowired
    CommonCRUD crud;

    /**
     * 跳转到首屏，负责重定向
     *
     * @param model
     * @param redirect 重定向位置
     * @return 跳转页面
     */
    @GetMapping("")
    public String splash(Model model, String redirect, HttpServletRequest request, Authentication authentication) {
        model.addAttribute("direction", redirect);
        if (request.getSession(false) == null) {
            logger.debug("No session! create one, new session is:{}", request.getSession(true).getId());
        }
        return "framework/splash";
    }

    @GetMapping("web")
    public String index() {
        return "redirect:/web/index";
    }

    @GetMapping("/web/{page}")
    @Order()
    public String router(@PathVariable String page, Authentication authentication) {
        if (PUBLIC_PAGES.contains(page)) {
            return String.format("/pub/web/%s", page);
        }
        return String.format("/priv/%s/%s", getUserType(authentication), page);
    }

    @Controller
    @RequestMapping("/web/hiring")
    public class HiringController extends ControllerBase {
        @GetMapping("new/edit")
        @Order(1)
        public String hiring(Model model, Authentication authentication) {
            model.addAttribute("disabled", "");
            model.addAttribute("create", "true");
            model.addAttribute("hiring", new Hiring());
            model.addAttribute("entrepreneurial", entrepreneurialService.getEntrepreneurial(getUsername(authentication)));
            return "/pub/web/hiring";
        }

        @PostMapping("new")
        @ResponseBody
        public String hiring(Hiring hiring, Authentication authentication, HttpServletResponse response) {
            hiring.setPubtime(new Timestamp(System.currentTimeMillis()));
            hiring.setUsername(getUsername(authentication));
            hiring = crud.newRecord(hiring, getUsername(authentication));
            return showSuccessMsgAndVisit("发布成功，正在跳转...", "/web/hiring/" + hiring.getId(), response);
        }

        @GetMapping("{id}/edit")
        @Order(2)
        public String edit(@PathVariable Long id, Model model, Authentication authentication) {
            Hiring hiring = hiringService.getHiring(id);
            model.addAttribute("disabled", "disabled");
            model.addAttribute("create", false);
            model.addAttribute("hiring", hiring);
            model.addAttribute("entrepreneurial", hiringService.getEntrepreneurial(hiring));
            if (hiring.getUsername().equals(getUsername(authentication)))
                model.addAttribute("editable", true);
            else
                model.addAttribute("editable", false);
            return "/pub/web/hiring";
        }

        @GetMapping("{id}")
        @Order(2)
        public String hiring(@PathVariable Long id, Model model, Authentication authentication) {
            Hiring hiring = hiringService.getHiring(id);
            model.addAttribute("disabled", "disabled");
            model.addAttribute("create", false);
            model.addAttribute("hiring", hiring);
            model.addAttribute("entrepreneurial", hiringService.getEntrepreneurial(hiring));
            if (hiring.getUsername().equals(getUsername(authentication)))
                model.addAttribute("editable", true);
            else
                model.addAttribute("editable", false);
            return "/pub/web/hiring";
        }
    }
}
