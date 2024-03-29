package r4g19.offer100.service.cym;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import r4g19.offer100.jooq.tables.daos.LoginDao;
import r4g19.offer100.jooq.tables.pojos.Login;
import r4g19.offer100.properties.cym.mapping.UserType;
import r4g19.offer100.service.ServiceBase;
import r4g19.offer100.utils.cym.LogMsgHelper;

import java.sql.Timestamp;

@Service
public class LoginService extends ServiceBase {
    private final PasswordEncoder passwordEncoder;

    public LoginService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public UserType getLoginType(String username) {
        return getLogin(username).getUserType();
    }

    public Login getLogin(String username) {
        return new LoginDao(dsl.configuration()).fetchOneByUsername(username);

    }

    /**
     * 创建新的客户登录信息
     *
     * @param password 密码（明文）
     * @param username 登录名
     * @param userType 登录类型
     * @return 新添加的登录信息
     * @throws LoginNameExistsException 登录名已被占用
     */
    public Login newLoginWithPassword(String username, String password, UserType userType) throws
            LoginNameExistsException {
        Login login = new Login();
        login.setPassword(password);
        login.setUsername(username);
        login.setUserType(userType);
        return newLogin(login);
    }

    /**
     * @param login 待添加的登录信息
     * @return 被添加的登录信息
     * @throws LoginNameExistsException 登录名已被占用
     */
    @Transactional
    public Login newLogin(Login login) throws LoginNameExistsException {
        LoginDao dao = new LoginDao(dsl.configuration());
        if (dao.existsById(login.getUsername()))
            throw new LoginNameExistsException("login name exists:" + login.getUsername());
        login.setJoinTime(new Timestamp(System.currentTimeMillis()));
        login.setPassword(passwordEncoder.encode(login.getPassword()));
        dao.insert(login);
        dbLogger.log(LogMsgHelper.Auth.newLogin(login));
        return login;
    }

    public String encode(String plainText) {
        return passwordEncoder.encode(plainText);
    }


    public interface Register<T extends Login> {
        /**
         * 注册一个新的用户
         *
         * @param login 用户信息
         * @return 新注册用户的信息
         * @throws LoginNameExistsException 登录名已被占用
         */
        @Transactional
        T register(T t, Login login) throws LoginNameExistsException;
    }

    public static class LoginNameExistsException extends RuntimeException {
        public LoginNameExistsException(String message) {
            super(message);
        }
    }
}
