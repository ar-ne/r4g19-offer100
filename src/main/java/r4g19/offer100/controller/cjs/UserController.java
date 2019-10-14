package r4g19.offer100.controller.cjs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
import r4g19.offer100.service.cym.CommonCRUD;
import r4g19.offer100.service.cym.LoginService;
import r4g19.offer100.utils.cjs.AliyunSMS;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class UserController extends ControllerBase {
    @Autowired
    UserService.PersonalService personalService;
    @Autowired
    UserService.EntrepreneurialService entrepreneurialService;
    @Autowired
    LoginService loginService;
    @Autowired
    UserService userService;
    @Autowired
    CommonCRUD crud;
    @Autowired
    AliyunSMS aliyunSMS;

    @GetMapping("login")
    public String login() {
        return "pub/login";
    }

    @GetMapping("logout")
    public String logout() {
        return "pub/logout";
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
    public String signup_p(Personal personal, Login login, String code, HttpSession session, HttpServletResponse response) {
        if (((SmsCode) session.getAttribute("code")).equals(code, login.getTel())) {
            login.setVerifyType(VerifyType.PHONE);
            personalService.register(personal, login);
            return showSuccessMsgAndVisit("注册成功", "/login", response);
        }
        response.setStatus(Status.showFail);
        return "";
    }

    @ResponseBody
    @PostMapping("signup/e")
    public String signup_e(Entrepreneurial entrepreneurial, Login login, HttpServletResponse response) {
        entrepreneurialService.register(entrepreneurial, login);
        return showSuccessMsgAndVisit("注册成功", "/login", response);
    }

    @GetMapping("/web/profile")
    @Order(1)
    public String profile1(Authentication authentication, Model model) {
        Login login = loginService.getLogin(getUsername(authentication));
        switch (login.getUserType()) {
            case Personal:
                model.addAttribute("userDetail", userService.getPersonal(getUsername(authentication)));
                break;
            case Entrepreneurial:
                model.addAttribute("userDetail", userService.getEntrepreneurial(getUsername(authentication)));
                break;
        }
        model.addAttribute("login", login);
        return "pub/web/profile";
    }

    @ResponseBody
    @PostMapping("/web/profile")
    public String profile(Authentication authentication, Login login, Personal personal, Entrepreneurial entrepreneurial, HttpServletResponse response) {
        String username = getUsername(authentication);
        switch (getUserType(authentication)) {
            case Entrepreneurial:
                crud.updateRecord(getUpdatedObject(entrepreneurial, userService.getEntrepreneurial(username)), username);
                break;
            case Personal:
                crud.updateRecord(getUpdatedObject(personal, userService.getPersonal(username)), username);
                break;
        }
        crud.updateRecord(getUpdatedObject(login, loginService.getLogin(username)), username);
        return showSuccessMsgAndVisit("修改成功", "/web/profile", response);
    }

    @GetMapping("/pswrestore")
    @Order(1)
    public String pswrestore1(Authentication authentication, Model model) {
        return "/pub/pswrestore";
    }

    @ResponseBody
    @PostMapping("/pswrestore")
    public String pswrestore(Login login, String code, HttpSession session, HttpServletResponse response) {
        if (((SmsCode) session.getAttribute("code")).equals(code, login.getTel())) {
            login.setPassword(loginService.encode(login.getPassword()));
            crud.updateRecord(getUpdatedObject(login, loginService.getLogin(login.getUsername())), login.getUsername());
            return showSuccessMsgAndVisit("密码修改成功", "/login", response);
        }
        response.setStatus(Status.showFail);
        return "";
    }

    @GetMapping("/web/pswreset")
    @Order(1)
    public String pswrepswreset1(Authentication authentication, Model model) {
        return "/pub/web/pswreset";
    }

    @ResponseBody
    @PostMapping("/web/pswreset")
    public String pswrepswreset(Authentication authentication, Login login, HttpServletResponse response) {
        login.setPassword(loginService.encode(login.getPassword()));
        crud.updateRecord(getUpdatedObject(login, loginService.getLogin(getUsername(authentication))), getUsername(authentication));
        return showSuccessMsgAndVisit("密码重置成功", "/login", response);
//        response.setStatus(Status.showFail);
//        return "";
    }
}
