package r4g19.offer100.service.cjs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import r4g19.offer100.jooq.tables.daos.EntrepreneurialDao;
import r4g19.offer100.jooq.tables.daos.PersonalDao;
import r4g19.offer100.jooq.tables.pojos.Entrepreneurial;
import r4g19.offer100.jooq.tables.pojos.Login;
import r4g19.offer100.jooq.tables.pojos.Personal;
import r4g19.offer100.properties.cym.mapping.UserType;
import r4g19.offer100.service.cym.CommonCRUD;
import r4g19.offer100.service.cym.LoginService;
import r4g19.offer100.service.ServiceBase;

/**
 * 所有用户的Service
 */
@Service
public class UserService extends ServiceBase {
    protected final LoginService loginService;
    protected final CommonCRUD crud;

    public UserService(CommonCRUD crud, LoginService loginService) {
        this.crud = crud;
        this.loginService = loginService;
    }

    /**
     * @个人用户注册服务
     * @return 插入记录
     */
    @Service
    public class PersonalService extends ServiceBase implements LoginService.Register<Personal> {

        @Override
        public Personal register(Personal personal, Login login) throws LoginService.LoginNameExistsException {
            login = loginService.newLogin(login);
            personal.setUsername(login.getUsername());
            return crud.newRecord(personal, login.getUsername());

        }
    }
    /**
     * @企业用户注册服务
     * @return 插入记录
     */
    @Service
    public class EntrepreneurialService extends ServiceBase implements LoginService.Register<Entrepreneurial> {
        @Override
        public Entrepreneurial register(Entrepreneurial entrepreneurial, Login login) throws LoginService.LoginNameExistsException {
            login = loginService.newLogin(login);
            entrepreneurial.setUsername(login.getUsername());
            return crud.newRecord(entrepreneurial,login.getUsername());
        }
    }

    /**
     * 从Personal数据库中获取一个用户
     *
     * @param loginname 登录名
     * @return 个人
     */
    public Personal getPersonal(String loginname) {
        switch (loginService.getLoginType(loginname)) {
            case Personal:
                return new PersonalDao(dsl.configuration()).fetchOneByUsername(loginname);
        }
        throw new RuntimeException("Error when trying to get Personal's detail");
    }

    /**
     * 从Entrepreneurial数据库中获取一个用户
     *
     * @param loginname 登录名
     * @return 企业
     */
    public Entrepreneurial getEntrepreneurial(String loginname) {
        switch (loginService.getLoginType(loginname)) {
            case Entrepreneurial:
                return new EntrepreneurialDao(dsl.configuration()).fetchOneByUsername(loginname);
        }
        throw new RuntimeException("Error when trying to get Entrepreneurial's detail");
    }

//    /**
//     * 根据身份信息获取用户
//     *
//     * @param authentication 身份信息
//     * @return 用户
//     */
//    public User getUser(Authentication authentication) {
//        String login = ((org.springframework.security.core.userdetails.User) authentication.getPrincipal()).getUsername();
//        return getUser(login);
//    }

//    /**
//     * 创建一个新的用户
//     *
//     * @param username     登录名
//     * @param plainTextPass 明文密码
//     * @param userType      用户类型
//     * @param user          用户
//     * @return 返回用户
//     * @throws LoginService.LoginNameExistsException
//     */
//    protected Personal createNewPersonal(String username, String plainTextPass, UserType userType, User user) throws LoginService.LoginNameExistsException {
//        Login login = loginService.newLoginWithPassword(username, plainTextPass, userType);
//        user.setu(login.getLoginname());
//        dbLogger.log(String.format("Welcome new %s with login name: %s", userType.name(), login.getLoginname()));
//        return user;
//    }


}
