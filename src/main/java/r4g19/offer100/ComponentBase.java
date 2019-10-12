package r4g19.offer100;

import com.rits.cloning.Cloner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import r4g19.offer100.properties.cym.mapping.UserType;
import r4g19.offer100.service.cym.DatabaseLog;

/**
 * 所有Component的父类
 */
@Component
public abstract class ComponentBase {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    protected final Cloner cloner = new Cloner();
    @Autowired
    protected DatabaseLog dbLogger;

    /**
     * 获取登录名
     *
     * @param authentication 身份
     * @return 登录名
     */
    public static String getUsername(Authentication authentication) {
        return ((User) authentication.getPrincipal()).getUsername();
    }

    public static UserType getUserType(Authentication authentication) {
        return ((UserType.Authority) authentication.getAuthorities().toArray()[0]).getUserType();
    }

    public <T> T deepClone(T o) {
        return cloner.deepClone(o);
    }
}
