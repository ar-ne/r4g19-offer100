package r4g19.offer100.controller.cjs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import r4g19.offer100.controller.ControllerBase;
import r4g19.offer100.jooq.tables.pojos.Entrepreneurial;
import r4g19.offer100.jooq.tables.pojos.Login;
import r4g19.offer100.jooq.tables.pojos.Personal;
import r4g19.offer100.model.cjs.SmsCode;
import r4g19.offer100.properties.cym.Status;
import r4g19.offer100.properties.cym.mapping.VerifyType;
import r4g19.offer100.service.cjs.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class Auth extends ControllerBase {
    @Autowired
    UserService.PersonalService personalService;
    @Autowired
    UserService.EntrepreneurialService entrepreneurialService;
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
    public String signup_p(Personal personal, Login login, String code, HttpSession session, HttpServletResponse response){
        if(((SmsCode)session.getAttribute("code")).equals(code,login.getTel())){
            login.setVerifyType(VerifyType.PHONE);
            personalService.register(personal,login);
            response.setStatus(Status.showSuccessMsg);
            return "注册成功";
        }
        response.setStatus(Status.showFail);
        return "";
    }
    @ResponseBody
    @PostMapping("signup/e")
    public String signup_e(Entrepreneurial entrepreneurial, Login login, HttpServletResponse response){
        entrepreneurialService.register(entrepreneurial,login);
        response.setStatus(Status.eval);
        return "showSuccessAlert('注册成功',3000,function(){Turbolinks.visit('/login')});";
    }
}
