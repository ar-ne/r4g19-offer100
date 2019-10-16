/**
 * 162012班 第四组
 * 智能售货机管理系统——XXX模块
 * FileName: Admin
 * Author:   J.Y
 * Date:     2019/10/12 8:48
 * Description:
 */

package r4g19.offer100.api.yhr;

import org.springframework.beans.factory.annotation.Autowired;
import r4g19.offer100.annotations.cym.API;
import r4g19.offer100.api.APIBase;
import r4g19.offer100.jooq.tables.daos.EntrepreneurialDao;
import r4g19.offer100.jooq.tables.daos.LoginDao;
import r4g19.offer100.jooq.tables.pojos.Login;
import r4g19.offer100.properties.cym.mapping.UserType;
import r4g19.offer100.service.yhr.AdminService;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.List;

@API("admin")
public class Admin extends APIBase {

    @Autowired
    AdminService adminService;

    @API("admin/personal")
    public class Personal extends APIBase {
        @GET
        @Path("/list")
        public List list() {
            List<Login> d = new LoginDao(dsl.configuration()).fetchByUserType(UserType.Personal);
            return d;
        }

        @DELETE
        @Path("delete/{username}")
        public void delete(String username) {
            adminService.deleteUser(username);
        }
    }

    @API("admin/enterprise")
    public class Enterprise extends APIBase {
        @GET
        @Path("/list")
        public List list() {
            List d = new EntrepreneurialDao(dsl.configuration()).findAll();
            return d;
        }

        @DELETE
        @Path("/delete/{username}")
        public void delete(String username) {
            adminService.deleteUser(username);
        }
    }


}

    