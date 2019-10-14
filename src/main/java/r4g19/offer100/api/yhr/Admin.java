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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import r4g19.offer100.annotations.cym.APIEntrance;
import r4g19.offer100.api.APIBase;
import r4g19.offer100.jooq.tables.daos.EntrepreneurialDao;
import r4g19.offer100.jooq.tables.daos.LoginDao;
import r4g19.offer100.jooq.tables.pojos.Login;
import r4g19.offer100.properties.cym.mapping.UserType;
import r4g19.offer100.service.yhr.AdminService;

import java.util.List;

@RestController
@RequestMapping("api/admin")
@APIEntrance(name = "admin")
public class Admin extends APIBase {

    @Autowired
    AdminService adminService;

    @RestController
    @RequestMapping("api/admin/personal")
    public class Personal extends APIBase {
        @GetMapping("/list")
        public HttpEntity list() {
            List<Login> d = new LoginDao(dsl.configuration()).fetchByUserType(UserType.Personal);
            return new ResponseEntity(d, HttpStatus.OK);
        }

        @DeleteMapping("delete/{username}")
        public void delete(@PathVariable String username) {
            adminService.deleteUser(username);
        }
    }

    @RestController
    @RequestMapping("api/admin/enterprise")
    public class Enterprise extends APIBase {
        @GetMapping("/list")
        public HttpEntity list() {
            List d = new EntrepreneurialDao(dsl.configuration()).findAll();
            return new ResponseEntity(d, HttpStatus.OK);
        }

        @DeleteMapping("/delete/{username}")
        public void delete(@PathVariable String username) {
            adminService.deleteUser(username);
        }
    }


}

    