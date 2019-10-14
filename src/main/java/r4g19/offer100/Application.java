package r4g19.offer100;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import r4g19.offer100.autoconfigure.cjs.aliyunSMS.AliyunSMSConfig;
import r4g19.offer100.properties.cjs.EmailConfig;
import r4g19.offer100.utils.cym.Auth;

/**
 * 入口类和配置信息
 */
@SpringBootApplication
@EnableCaching
@EnableConfigurationProperties({AliyunSMSConfig.class, EmailConfig.class})
public class Application {

    public static void main(String[] args) {
        new ConsoleThread().start();
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationSuccessHandler authSuccessHandler() {
        return new Auth.LoginHandler();
    }

    @Bean
    public AuthenticationFailureHandler authFailureHandler() {
        return new Auth.LoginHandler();
    }

    @Bean
    public LogoutSuccessHandler logoutHandler() {
        return new Auth.LogoutHandler();
    }

//    @Configuration
//    public static class ThymeleafConfig {
//        public ThymeleafConfig(SpringTemplateEngine engine) {
//            ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
//            resolver.setPrefix("templates/");
//            resolver.setSuffix(".html");
//            resolver.setOrder(engine.getTemplateResolvers().size());
//            resolver.setCharacterEncoding("UTF-8");
//            engine.addTemplateResolver(resolver);
//        }
//    }

    @Configuration
    @EnableWebSocketMessageBroker
    public static class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

        @Override
        public void configureMessageBroker(MessageBrokerRegistry config) {
            config.enableSimpleBroker("/topic");
            config.setApplicationDestinationPrefixes("/app");
        }

        @Override
        public void registerStompEndpoints(StompEndpointRegistry registry) {
            registry.addEndpoint("/ws").withSockJS();
        }
    }

    @Configuration
    @EnableGlobalMethodSecurity(
            prePostEnabled = true,
            securedEnabled = true,
            jsr250Enabled = true)
    public static class MethodSecurityConfig extends GlobalMethodSecurityConfiguration {
    }

    @Controller
    @Configuration
    @EnableWebSecurity
    public class WebSecurity extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests()
                    .antMatchers("/", "/webpack/**", "/fonts/**", "/scripts/**").permitAll()
                    .antMatchers("/styles/**", "/signup/e", "/signup/p", "/pswrestore", "/pswreset").permitAll()
                    .antMatchers("/admin/**", "/api/admin/**", "/devops/**").access("hasIpAddress('127.0.0.1') or hasIpAddress('0:0:0:0:0:0:0:1')")
                    .antMatchers("/web/**", "/api/user/**").access("hasAnyRole('Entrepreneurial','Personal')")
                    .antMatchers("/api/public/**").access("hasAnyRole('Entrepreneurial','Personal') or hasIpAddress('127.0.0.1') or hasIpAddress('0:0:0:0:0:0:0:1')")
                    .and().formLogin().loginPage("/login").successHandler(authSuccessHandler()).failureHandler(authFailureHandler()).permitAll()
                    .and().logout().logoutSuccessHandler(logoutHandler());
        }

    }
}
