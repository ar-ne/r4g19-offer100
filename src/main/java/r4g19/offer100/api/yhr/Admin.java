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
import r4g19.offer100.jooq.tables.daos.HiringDao;
import r4g19.offer100.jooq.tables.daos.LoginDao;
import r4g19.offer100.jooq.tables.daos.NoticeDao;
import r4g19.offer100.jooq.tables.pojos.Login;
import r4g19.offer100.properties.cym.mapping.UserType;
import r4g19.offer100.service.yhr.AdminService;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.List;

@API("admin")
public class Admin extends APIBase {
    @API("admin/personal")
    public static class Personal extends APIBase {
        @Autowired
        AdminService adminService;

        @GET
        @Path("/list")
        public List list() {
            List<Login> d = new LoginDao(dsl.configuration()).fetchByUserType(UserType.Personal);
            return d;
        }

        @DELETE
        @Path("delete")
        public void delete(String username) {
            adminService.deleteUser(username);
        }
    }

    @API("admin/enterprise")
    public static class Enterprise extends APIBase {
        @Autowired
        AdminService adminService;

        @GET
        @Path("/list")
        public List list() {
            List d = new EntrepreneurialDao(dsl.configuration()).findAll();
            return d;
        }

        @DELETE
        @Path("/delete")
        public void delete(String username) {
            adminService.deleteUser(username);
        }
    }

    @API("admin/notice")
    public static class Notice extends APIBase {
        @Autowired
        AdminService adminService;

        @GET
        @Path("/list")
        public List list() {
            List d = new NoticeDao(dsl.configuration()).findAll();
            return d;
        }

        @DELETE
        @Path("/delete")
        public void delete(Integer noticeId) {
            adminService.deleteNotice(noticeId);
        }
    }

    @API("admin/hr")
    public static class HR extends APIBase {
        @Autowired
        AdminService adminService;

        @GET
        @Path("/list")
        public List list() {
            List d = new HiringDao(dsl.configuration()).findAll();
            return d;
        }

        @DELETE
        @Path("/delete")
        public void delete(Long id) {
            adminService.deletehr(id);
        }
    }
}

    