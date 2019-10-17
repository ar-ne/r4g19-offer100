package r4g19.offer100.api.cjs;

import com.aliyuncs.exceptions.ClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import r4g19.offer100.annotations.cym.API;
import r4g19.offer100.api.APIBase;
import r4g19.offer100.model.cjs.SmsCode;
import r4g19.offer100.service.cym.LoginService;
import r4g19.offer100.utils.cjs.AliyunSMS;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import java.util.Map;

@API("public/verify")
public class Verify extends APIBase {
    @Autowired
    AliyunSMS aliyunSMS;
    @Autowired
    LoginService loginService;

    @POST
    @Path("phone")
    public String phone(String phoneNumber, @Context HttpServletRequest request) throws ClientException {
//        phoneNumber = phoneNumber.substring(1, phoneNumber.length() - 1);
        SmsCode smsCode = SmsCode.generate(phoneNumber);
        if (!logger.isDebugEnabled() && !aliyunSMS.sendSms(phoneNumber, smsCode.getJSON()).getCode().equals("OK")) {
            return "ERROR";
        }
        request.getSession().setAttribute("code", smsCode);
        if (logger.isDebugEnabled()) return smsCode.getCode();
        return "发送成功";
    }

    @POST
    @Path("restore")
    public HttpEntity<String> restore(Map<String, String> map, @Context HttpServletRequest request) throws ClientException {
        String username = map.get("username");
        String tel = map.get("tel");
        String phoneNumber = loginService.getLogin(username).getTel();
        SmsCode smsCode = SmsCode.generate(phoneNumber);
        if (!logger.isDebugEnabled() && !aliyunSMS.sendSms(phoneNumber, smsCode.getJSON()).getCode().equals("OK")) {
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
        request.getSession().setAttribute("code", smsCode);
        if (logger.isDebugEnabled()) return new ResponseEntity<>(smsCode.getCode(), HttpStatus.OK);
        return new ResponseEntity<>("发送成功", HttpStatus.OK);
    }
}
