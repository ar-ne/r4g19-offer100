package r4g19.offer100.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * 本项目的神奇之处
 * 页面路由，将对应的页面，对应到相应的view
 */
@Controller
public class Router extends ControllerBase {
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
        if (authentication != null && authentication.isAuthenticated()) {
            model.addAttribute("username", getUsername(authentication));
            model.addAttribute("userType", getUserType(authentication));
        }
        return "framework/splash";
    }

    @GetMapping("web")
    public String index(Authentication authentication) {
        return "redirect:/web/index";
    }

    @GetMapping("/web/{page}")
    public String router(@PathVariable String page, Authentication authentication) {
        return String.format("/priv/%s/%s", getUserType(authentication), page);
    }
}
