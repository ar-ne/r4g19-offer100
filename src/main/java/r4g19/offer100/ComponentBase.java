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

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;

/**
 * 所有Component的父类
 */
@Component
public abstract class ComponentBase {
    protected static final Cloner cloner = new Cloner();
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
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

    public <T> T getUpdatedObject(T uo, T dbo) {
        try {
            Class clazz = uo.getClass();

            for (Field field : clazz.getDeclaredFields()) { //遍历类属性，包括私有属性
                if (field.getName().equalsIgnoreCase("serialVersionUID")) continue;
                boolean dboAcc = field.canAccess(dbo);  //获取属性原本是否可写
                field.setAccessible(true); //设置可写
                if (field.get(uo) != null)
                    field.set(dbo, field.get(uo));  //更新类属性
                field.setAccessible(dboAcc); //还原是否可写
            }
            return dbo;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return uo;
    }

    /**
     * only return local or anonymous
     *
     * @param request
     * @return
     */
    public UserType getUserType(HttpServletRequest request) {
        if (request.getRemoteHost().equalsIgnoreCase("127.0.0.1") ||
                request.getRemoteHost().equalsIgnoreCase("0:0:0:0:0:0:0:1"))
            return UserType.Localhost;
        return UserType.Anonymous;
    }
}
