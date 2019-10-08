package r4g19.offer100.utils.cym;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import r4g19.offer100.ComponentBase;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 认证
 */
public class Auth {
    /**
     * 登录处理
     */
    public static class LoginHandler extends ComponentBase implements AuthenticationSuccessHandler, AuthenticationFailureHandler {
        /**
         * 登录成功
         *
         * @param request        请求
         * @param response       相应
         * @param authentication 身份
         * @throws IOException
         * @throws ServletException
         */
        @Override
        public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//            dbLogger.log(LogMsgHelper.Auth.loginAttempt(authentication.getName(), authentication.isAuthenticated()));
            response.getWriter().print("/web/index");
            response.setStatus(201);
        }

        /**
         * 登录失败
         *
         * @param request   请求
         * @param response  相应
         * @param exception 认证错误
         * @throws IOException
         * @throws ServletException
         */
        @Override
        public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
            response.getWriter().print("Wrong login");
            response.setStatus(403);
        }
    }

    /**
     * 登出处理
     */
    public static class LogoutHandler implements LogoutSuccessHandler {
        /**
         * 登出成功
         *
         * @param request        请求
         * @param response       相应
         * @param authentication 身份
         * @throws IOException
         * @throws ServletException
         */
        @Override
        public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
            response.getWriter().print("/web/");
            response.setStatus(201);
        }
    }

    public class AuthAttemptListeners {

    }
}
