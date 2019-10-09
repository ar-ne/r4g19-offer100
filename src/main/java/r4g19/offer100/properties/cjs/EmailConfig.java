package r4g19.offer100.properties.cjs;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

@Data
@Configuration
@ConfigurationProperties(prefix = "email")
public class EmailConfig {
    /**
     * 邮箱账号
     */
    private String username;
    /**
     * 邮箱密码
     */
    private String password;
    private POP pop=new POP();
    private SMTP smtp=new SMTP();

    @Data
    public static class POP {
        private String host = "pop.qq.com";
        private Integer port = 995;
    }

    @Data
    public static class SMTP {
        private String host = "smtp.qq.com";
        private Integer port = 465;
    }
}
