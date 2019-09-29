package r4g19.offer100.cjs;

import org.springframework.web.bind.annotation.RequestBody;
import r4g19.offer100.jooq.tables.pojos.Login;

import java.util.HashMap;
import java.util.Map;


public class Smsservice {
    private String code;
    public void createSmsCode(String phone) {
        this.code = (long) (Math.random() * 1000000) + ""; //生成随机6位短信验证码
        System.out.println("验证码：" + code);

        //存入缓存。。。
    }

    public Map<String,Object> register(@RequestBody Login login, String smsCode) {
        Map<String,Object> result = new HashMap<String,Object>() ;
        if(login.equals(" ") && smsCode.equals(code)){
            result.put("验证码输入正确！",true);

        }else {
            result.put("验证码输入错误！",false);
        }
        return result;

    }

}
