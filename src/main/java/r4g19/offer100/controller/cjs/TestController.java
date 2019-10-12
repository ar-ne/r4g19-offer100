package r4g19.offer100.controller.cjs;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import r4g19.offer100.service.cjs.AliyunSmsSenderServiceImpl;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 测试发送短信controller
 */
@RestController
public class TestController {

    @Autowired
    private AliyunSmsSenderServiceImpl aliyunSmsSenderServiceImpl;


    /**
     * @Description: 短信发送
     */
    @GetMapping("sms")
    @ResponseBody
    public String sms() {
        Map<String, String> map = new HashMap<>();
        map.put("sellerName", "平台自营");
        map.put("orderSn", "P2019041895451");
        SendSmsResponse sendSmsResponse = aliyunSmsSenderServiceImpl.sendSms("13340110608",
                JSON.toJSONString(map),
                "SMS_175051522");
        return JSON.toJSONString(sendSmsResponse);
    }

    /**
     * @Description: 短信查询
     */
    @GetMapping("/query")
    @ResponseBody
    public String query() {
        QuerySendDetailsResponse querySendDetailsResponse = aliyunSmsSenderServiceImpl.querySendDetails("此处填写发送短信返回的bizId",
                "此处填写手机号", 10L, 1L);
        return JSON.toJSONString(querySendDetailsResponse);
    }
}
