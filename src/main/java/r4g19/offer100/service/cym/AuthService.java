package r4g19.offer100.service.cym;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import r4g19.offer100.jooq.tables.daos.LoginDao;
import r4g19.offer100.jooq.tables.pojos.Login;
import r4g19.offer100.service.ServiceBase;

import java.util.Collections;

/**
 * 登录认证 SpringSecurity
 */
@Service
public class AuthService extends ServiceBase implements UserDetailsService {

    /**
     * 从数据库加载一个用户
     *
     * @param loginName 登录名
     * @return 用户信息
     * @throws UsernameNotFoundException 如果登录名不存在
     */
    @Override
    public UserDetails loadUserByUsername(String loginName) throws UsernameNotFoundException {
        Login login = new LoginDao(dsl.configuration()).fetchOneByUsername(loginName);
        if (login == null) throw new UsernameNotFoundException("login name:" + loginName);
        return new User(login.getUsername(), login.getPassword(), Collections.singletonList(login.getType().getAuthority()));
    }


}