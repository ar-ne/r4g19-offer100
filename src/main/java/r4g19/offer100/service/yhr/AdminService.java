/**
 * 162012班 第四组
 * 智能售货机管理系统——XXX模块
 * FileName: AdminService
 * Author:   J.Y
 * Date:     2019/10/8 8:26
 * Description:
 */

package r4g19.offer100.service.yhr;

import org.jooq.*;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Service;
import r4g19.offer100.jooq.tables.Login;
import r4g19.offer100.jooq.tables.daos.EntrepreneurialDao;
import r4g19.offer100.jooq.tables.daos.LoginDao;
import r4g19.offer100.jooq.tables.pojos.Entrepreneurial;
import r4g19.offer100.service.ServiceBase;

import java.util.Collection;


/**
 * 功能描述: 
 * @author J.Y
 * @create 2019/10/8
 * @since 1.0.0
 */
@Service
public class AdminService extends ServiceBase {

    public void deleteUser(String username){
       new LoginDao(dsl.configuration()).deleteById(username);
    }
}

    