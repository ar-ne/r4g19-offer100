package r4g19.offer100.service.cym;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import r4g19.offer100.jooq.tables.daos.EntrepreneurialDao;
import r4g19.offer100.jooq.tables.pojos.Entrepreneurial;
import r4g19.offer100.jooq.tables.pojos.Login;
import r4g19.offer100.service.ServiceBase;

import static r4g19.offer100.utils.cym.ReflectUtils.mergeWithSuper;

@Service
public class EntrepreneurialService extends ServiceBase {
    @Autowired
    LoginService loginService;

    public Entrepreneurial getEntrepreneurial(String username) {
        Login login = loginService.getLogin(username);
        Entrepreneurial entrepreneurial = new EntrepreneurialDao(dsl.configuration()).fetchOneByUsername(username);
        return mergeWithSuper(entrepreneurial, login);
    }
}
