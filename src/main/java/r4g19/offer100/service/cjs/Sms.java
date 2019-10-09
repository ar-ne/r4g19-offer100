package r4g19.offer100.service.cjs;

import lombok.Data;
import lombok.ToString;

/**
 * @发送短信实体
 */
@Data
@ToString
public class Sms {

    /**
     * 手机号
     */
    private String phoneNumbers;

    /**
     * 模板参数 格式："{\"code\":\"123456\"}"
     */
    private String templateParam;

    private String outId;

    /**
     * 阿里云模板管理code
     */
    private String templateCode;
}