package r4g19.offer100.controller.cym;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import r4g19.offer100.controller.ControllerBase;
import r4g19.offer100.jooq.tables.pojos.Entrepreneurial;
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
        @GetMapping("new")
        @Order(1)
        public String hiring(Model model, Authentication authentication) {
            setAttributes(false, true, false, false,
                    new Hiring(), entrepreneurialService.getEntrepreneurial(getUsername(authentication)), model);
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

        @GetMapping("{id}/{operate}")
        @Order(2)
        public String edit(@PathVariable Long id, Hiring newHiring, Model model, Authentication authentication, @PathVariable String operate) {
            Hiring hiring = hiringService.getHiring(id);
            if (operate.equalsIgnoreCase("edit") &&
                    hiringService.getEntrepreneurial(hiring).getUsername().equals(getUsername(authentication))) {
                setAttributes(false, false, true, hiring.getUsername().equals(getUsername(authentication)), hiring, hiringService.getEntrepreneurial(hiring), model);
                return "/pub/web/hiring";
            }
            if (operate.equalsIgnoreCase("del")) {
                if (hiring.getUsername().equals(getUsername(authentication)))
                    hiringService.delete(hiring.getId());
            }
            if (operate.equalsIgnoreCase("save") && hiringService.getEntrepreneurial(hiring).getUsername().equals(getUsername(authentication))) {
                crud.updateRecord(getUpdatedObject(newHiring, hiring), getUsername(authentication));
            }
            return "redirect:/web/hiring_mang";
        }

        @PostMapping("{id}/save")
        @ResponseBody
        @Order(2)
        public String save(@PathVariable Long id, Hiring newHiring, Authentication authentication, HttpServletResponse response) {
            Hiring hiring = hiringService.getHiring(id);
            if (hiringService.getEntrepreneurial(hiring).getUsername().equals(getUsername(authentication))) {
                crud.updateRecord(getUpdatedObject(newHiring, hiring), getUsername(authentication));
            }
            return showSuccessMsgAndVisit("修改成功，正在跳转...", "/web/hiring/" + hiring.getId(), response);
        }

        @GetMapping("{id}")
        @Order(2)
        public String hiring(@PathVariable Long id, Model model, Authentication authentication) {
            Hiring hiring = hiringService.getHiring(id);
            setAttributes(true, false, false,
                    hiring.getUsername().equals(getUsername(authentication)), hiring, hiringService.getEntrepreneurial(hiring), model);
            return "/pub/web/hiring";
        }

        private Model setAttributes(boolean disabled, boolean creating, boolean editing, boolean userEditable, Hiring hiring, Entrepreneurial entrepreneurial, Model model) {
            model.addAttribute("disabled", disabled ? "disabled" : "");
            model.addAttribute("creating", creating);
            model.addAttribute("editing", editing);
            model.addAttribute("userEditable", userEditable);
            model.addAttribute("hiring", hiring);
            model.addAttribute("entrepreneurial", entrepreneurial);
            return model;
        }
    }
}
