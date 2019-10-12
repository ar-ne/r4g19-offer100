package r4g19.offer100.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import r4g19.offer100.ComponentBase;
import r4g19.offer100.properties.cym.mapping.UserType;

@Controller
public abstract class ControllerBase extends ComponentBase {

    @ModelAttribute("userType")
    private UserType setUserType(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated())
            return getUserType(authentication);
        return null;
    }


    @ModelAttribute("username")
    private String setUsername(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated())
            return getUsername(authentication);
        return "";
    }
}
