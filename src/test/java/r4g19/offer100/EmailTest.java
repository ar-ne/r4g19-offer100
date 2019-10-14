package r4g19.offer100;

public class EmailTest {
//    @RunWith(SpringJUnit4ClassRunner.class)
//    @ContextConfiguration("classpath:applicationContext.xml")
//    public class Email_Test {
//        @Autowired
//        EmailUtill emailSender;
//        @Autowired
//        EmailUtill emailUtill;
//        @Value("${email.username}")
//        private String  username;
//
//
//        // 简单邮件
//        @Test
//        public void SimpleMailMessage() throws Exception {
//            SimpleMailMessage mailMessage = new SimpleMailMessage();
//            mailMessage.setFrom(username);//        发送人
//            mailMessage.setTo("605271504@qq.com");//          收件人
//            mailMessage.setSubject("隐世集团");//               标题
//            mailMessage.setText("隐无为-祝福你");            //            内容
//            emailSender.send(mailMessage);
//        }
//
//        //  html 文件
//        @Test
//        public void htmlMailMessage() {
//            MimeMessage mimeMsg = emailSender.createMimeMessage();
//            try {
//                String html = "<h1>隐世集团祝福你</h1>";
//                MimeMessageHelper helper = new MimeMessageHelper(mimeMsg, true);
//                helper.setTo(username);
//                helper.setFrom("605271504@qq.com");
//                helper.setSubject("隐世集团");
//                helper.setText(html, true);
//                emailSender.send(mimeMsg);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//        }
//    }
}
