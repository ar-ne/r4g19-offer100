/**
 * 162012班 第四组
 * 智能售货机管理系统——XXX模块
 * FileName: AdminService
 * Author:   J.Y
 * Date:     2019/10/8 8:26
 * Description:
 */

package r4g19.offer100.service.yhr;

import org.springframework.stereotype.Service;
import r4g19.offer100.jooq.Tables;
import r4g19.offer100.jooq.tables.daos.EntrepreneurialDao;
import r4g19.offer100.jooq.tables.daos.LogDao;
import r4g19.offer100.jooq.tables.daos.LoginDao;
import r4g19.offer100.jooq.tables.daos.PersonalDao;
import r4g19.offer100.service.ServiceBase;



/**
 * 功能描述: 
 * @author J.Y
 * @create 2019/10/8
 * @since 1.0.0
 */
@Service
public class AdminService extends ServiceBase {

    public void deleteUser(String username) {
        new LogDao(dsl.configuration()).deleteById();
        dsl.delete(Tables.COLLECTION).where(Tables.COLLECTION.PER_USERNAME.eq(username)).execute();
        dsl.delete(Tables.COLLECTION).where(Tables.COLLECTION.HIR_USERNAME.eq(username)).execute();
        dsl.delete(Tables.SUBMISSION).where(Tables.SUBMISSION.RES_USERNAME.eq(username)).execute();
        dsl.delete(Tables.SUBMISSION).where(Tables.SUBMISSION.HIR_USERNAME.eq(username)).execute();
        dsl.delete(Tables.HIRING).where(Tables.HIRING.USERNAME.eq(username)).execute();
        new EntrepreneurialDao(dsl.configuration()).deleteById(username);
        dsl.delete(Tables.RESUME).where(Tables.RESUME.USERNAME.eq(username)).execute();
        new PersonalDao(dsl.configuration()).deleteById(username);
        new LoginDao(dsl.configuration()).deleteById(username);

    }
}

    