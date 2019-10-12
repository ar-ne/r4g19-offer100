package r4g19.offer100.api.cym;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import r4g19.offer100.annotations.cym.APIEntrance;
import r4g19.offer100.api.APIBase;
import r4g19.offer100.jooq.tables.daos.HiringDao;

import java.util.List;

@RestController
@RequestMapping("api/user")
@APIEntrance(name = "user")
public class User extends APIBase {
    @RestController
    @RequestMapping("api/user/entrepreneurial")
    public static class Entrepreneurial extends APIBase {
        @GetMapping("hiring/list")
        public HttpEntity<List> hiringList(Authentication authentication) {
            return new ResponseEntity<>(new HiringDao(dsl.configuration()).fetchByUsername(getUsername(authentication)), HttpStatus.OK);
        }
    }
}
