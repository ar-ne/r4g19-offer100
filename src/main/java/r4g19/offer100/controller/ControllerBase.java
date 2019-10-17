package r4g19.offer100.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import r4g19.offer100.ComponentBase;
import r4g19.offer100.jooq.tables.pojos.Login;
import r4g19.offer100.properties.cym.Status;
import r4g19.offer100.properties.cym.mapping.UserType;
import r4g19.offer100.service.cym.LoginService;
import r4g19.offer100.utils.cym.ByteToStringConverter;

import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;

@Controller
public abstract class ControllerBase extends ComponentBase {
    @Autowired
    private LoginService loginService;

    public String showSuccessMsgAndVisit(String msg, String page, HttpServletResponse response) {
        response.setStatus(Status.eval);
        return String.format("showSuccessAlert('%s',3000,function(){Turbolinks.visit('%s')});", msg, page);
    }

    @ModelAttribute("userType")
    private UserType setUserType(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated())
            return getUserType(authentication);
        return null;
    }

    @ModelAttribute("simpleDateFormat")
    private SimpleDateFormat setSimpleDateFormat() {
        return new SimpleDateFormat("yyyy-MM-dd");
    }

    @ModelAttribute("byteToString")
    private ByteToStringConverter byteToString() {
        return new ByteToStringConverter();
    }


    @ModelAttribute("username")
    private String setUsername(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated())
            return getUsername(authentication);
        return null;
    }

    @ModelAttribute("login")
    private Login setLogin(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated())
            return loginService.getLogin(getUsername(authentication));
        return null;
    }
}
