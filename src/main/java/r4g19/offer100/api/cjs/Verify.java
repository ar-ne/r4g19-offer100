package r4g19.offer100.api.cjs;

import com.aliyuncs.exceptions.ClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import r4g19.offer100.api.APIBase;
import r4g19.offer100.model.cjs.SmsCode;
import r4g19.offer100.service.cym.LoginService;
import r4g19.offer100.utils.cjs.AliyunSMS;

import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
@RequestMapping("api/public/verify")
public class Verify extends APIBase {
    @Autowired
    AliyunSMS aliyunSMS;
    @Autowired
    LoginService loginService;

    @PostMapping("phone")
    public HttpEntity<String> phone(@RequestBody String phoneNumber, HttpSession httpSession) throws ClientException {
        phoneNumber = phoneNumber.substring(1, phoneNumber.length() - 1);
        SmsCode smsCode = SmsCode.generate(phoneNumber);
        if (!logger.isDebugEnabled() && !aliyunSMS.sendSms(phoneNumber, smsCode.getJSON()).getCode().equals("OK")) {
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
        httpSession.setAttribute("code", smsCode);
        if (logger.isDebugEnabled()) return new ResponseEntity<>(smsCode.getCode(), HttpStatus.OK);
        return new ResponseEntity<>("发送成功", HttpStatus.OK);
    }

    @PostMapping("restore")
    public HttpEntity<String> restore(@RequestBody Map<String, String> params, HttpSession httpSession) throws ClientException {
        String username = params.get("username");
        String tel = params.get("tel");
        String phoneNumber = loginService.getLogin(username).getTel();
        SmsCode smsCode = SmsCode.generate(phoneNumber);
        if (!logger.isDebugEnabled() && !aliyunSMS.sendSms(phoneNumber, smsCode.getJSON()).getCode().equals("OK")) {
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
        httpSession.setAttribute("code", smsCode);
        if (logger.isDebugEnabled()) return new ResponseEntity<>(smsCode.getCode(), HttpStatus.OK);
        return new ResponseEntity<>("发送成功", HttpStatus.OK);
    }
}
