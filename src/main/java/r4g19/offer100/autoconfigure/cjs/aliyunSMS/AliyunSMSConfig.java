package r4g19.offer100.autoconfigure.cjs.aliyunSMS;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @阿里短信服务配置类
 */
@Data
@ConfigurationProperties(prefix = "aliyun.sms")
public class AliyunSMSConfig {

    /**
     * 阿里云 accessKeyId（安全信息管理下面）
     */
    private String accessKeyId;

    /**
     * 阿里云 accessKeySecret（安全信息管理下面）
     */
    private String accessKeySecret;

    /**
     * 产品名称:云通信短信API产品
     */
    private String product="Dysmsapi";

    /**
     * 产品域名,开发者无需替换
     */
    private String domain="dysmsapi.aliyuncs.com";


    private String regionId="cn-hangzhou";

    /**
     * 短信签名名称（国内/国际/港澳台消息-签名管理下面）
     */
    private String signName;

    /**
     * 发送日期 支持30天内记录查询，格式yyyyMMdd
     */
    private String dateFormat;

    /**
     * 服务结点
     */
    private String endpointName="cn-hangzhou";


    private  String templateCode;
}