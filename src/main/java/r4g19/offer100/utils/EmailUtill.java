package r4g19.offer100.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import r4g19.offer100.ComponentBase;
import r4g19.offer100.properties.cjs.EmailConfig;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;


@Component
public class EmailUtill extends ComponentBase {

    JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();

    @Autowired
    EmailConfig emailConfig;

    @Value("${email.username}")
    private String  username;


//    //@Bean
//    public MailSender javaMailSender() {
//        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
//        javaMailSender.setHost(emailConfig..host);
//        javaMailSender.setPort(pop.port);
//        javaMailSender.setUsername(username);
//        javaMailSender.setPassword(password);
//        return javaMailSender;
//    }

    //简单邮件
    public void sendMail(String To,String title,String content) throws Exception {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        //mailMessage.setFrom(username);          //        发送人
        mailMessage.setFrom("1721463423@qq.com");//          发送人
        mailMessage.setTo("1721463423@qq.com");  //          收件人
        mailMessage.setSubject("邮件标题啊这是");           //          标题
        mailMessage.setText("邮件内容啊这是");            //          内容
        javaMailSender.send(mailMessage);
    }
    //附件
    public void mimeEmail() throws MessagingException {
        // MimeMessage 本身的 API 有些笨重，我们可以使用 MimeMessageHelper
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        // 第二个参数是 true ，表明这个消息是 multipart类型的/
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setFrom("1721463423@qq.com");
        mimeMessageHelper.setTo("1721463423@qq.com");
        mimeMessageHelper.setSubject("附件邮件主题");
        mimeMessageHelper.setText("附件邮件内容");
        //添加附件,第一个参数表示添加到 Email 中附件的名称，第二个参数是图片资源
        mimeMessageHelper.addAttachment("boot.png", new ClassPathResource("public/images/boot.png"));
        javaMailSender.send(mimeMessage);
    }

}