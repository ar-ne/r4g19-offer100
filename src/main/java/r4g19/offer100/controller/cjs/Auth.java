package r4g19.offer100.controller.cjs;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import r4g19.offer100.controller.ControllerBase;
import r4g19.offer100.jooq.tables.pojos.Entrepreneurial;
import r4g19.offer100.jooq.tables.pojos.Login;
import r4g19.offer100.jooq.tables.pojos.Personal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class Auth extends ControllerBase {
    @GetMapping("login")
    public String login() {
        return "pub/login";
    }

    @GetMapping("signup/e")
    public String signup_e(HttpServletRequest request) {
        System.out.println(request);
        return "pub/signup/e";
    }
    @GetMapping("signup/p")
    public String signup_p() {
        return "pub/signup/p";
    }

    @ResponseBody
    @PostMapping("signup/p")
    public String signup_p(Personal personal, Login login,String code, HttpSession session){
        return "signup/p";
    }
    @ResponseBody
    @PostMapping("signup/e")
    public String signup_e(Entrepreneurial entrepreneurial, Login login, HttpSession session){
        return "signup/e";
    }
}
