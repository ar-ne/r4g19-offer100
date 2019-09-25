package r4g19.offer100.controller;

import r4g19.offer100.ComponentBase;
import r4g19.offer100.properties.mapping.UserType;
import r4g19.offer100.service.CommonCRUD;
import r4g19.offer100.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

import static r4g19.offer100.properties.Vars.PUBLIC_PAGES;

/**
 * 本项目的神奇之处
 * 页面路由，将对应的页面，对应到相应的view
 */
@Controller
@RequestMapping("/")
public class Router extends ComponentBase {
    private final UserService userService;
    private final CommonCRUD commonCRUD;

    public Router(UserService userService, CommonCRUD commonCRUD) {
        this.userService = userService;
        this.commonCRUD = commonCRUD;
    }

    /**
     * 跳转到首屏，负责重定向
     *
     * @param model
     * @param redirect 重定向位置
     * @return 跳转页面
     */
    @GetMapping("")
    public String splash(Model model, String redirect, HttpServletRequest request) {
        model.addAttribute("direction", redirect);
        if (request.getSession(false) == null) {
            logger.debug("No session! create one, new session is:{}",request.getSession(true).getId());
        }
        return "framework/splash";
    }
}
