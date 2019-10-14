/**
 * 162012班 第四组
 * 智能售货机管理系统——XXX模块
 * FileName: Admin
 * Author:   J.Y
 * Date:     2019/10/12 8:48
 * Description:
 */

package r4g19.offer100.api.yhr;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import r4g19.offer100.api.APIBase;
import r4g19.offer100.jooq.tables.daos.EntrepreneurialDao;
import r4g19.offer100.jooq.tables.daos.LoginDao;
import r4g19.offer100.jooq.tables.pojos.Login;

import java.util.List;

@RestController
@RequestMapping("api/admin")
public class Admin extends APIBase {

    @RestController
    @RequestMapping("api/admin/userList")
    public static class userList extends APIBase{
        @GetMapping("/tables")
        public HttpEntity table() {
            List<Login> d = new LoginDao(dsl.configuration()).findAll();
            return new ResponseEntity(d, HttpStatus.OK);
        }
    }

    @RestController
    @RequestMapping("api/admin/enterpriseList")
    public static class enterpriseList extends APIBase{
        @GetMapping("/tables")
        public HttpEntity table() {
            List d = new EntrepreneurialDao(dsl.configuration()).findAll();
            return new ResponseEntity(d, HttpStatus.OK);
        }
    }
}

    