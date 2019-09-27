package r4g19.offer100.controller.cjs;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import r4g19.offer100.controller.ControllerBase;

@Controller
public class Auth extends ControllerBase {
    @GetMapping("login")
    public String login() {
        return "pub/login";
    }

    @GetMapping("signup")
    public String signup() {
        return "pub/signup";
    }
}
