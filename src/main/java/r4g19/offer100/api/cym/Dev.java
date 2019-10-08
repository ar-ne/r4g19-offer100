package r4g19.offer100.api.cym;

import org.jooq.Field;
import org.jooq.Table;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import r4g19.offer100.api.APIBase;
import r4g19.offer100.jooq.tables.pojos.Login;

import java.util.ArrayList;
import java.util.List;

import static r4g19.offer100.jooq.Public.PUBLIC;

@RestController
@RequestMapping("api/dev")
public class Dev extends APIBase {

    @RestController
    @RequestMapping("api/dev/sample")
    public static class Sample extends APIBase {
        @GetMapping("tables/{t}")
        public HttpEntity tableSample(@PathVariable String t) {
            List<Login> logins = new ArrayList<>();
            for (int i = 0; i < 100; i++) {
                logins.add(new Login());
            }
            return new ResponseEntity(logins, HttpStatus.OK);
        }
    }

    @RestController
    @RequestMapping("api/dev/info")
    public static class Info extends APIBase {
        @GetMapping("/tables")
        public HttpEntity table() {
            List<String> list = new ArrayList<>();
            for (Table<?> table : PUBLIC.getTables()) {
                for (Field<?> field : table.fields()) {
                    list.add(table.getName() + "." + field.getName().toLowerCase());
                }
            }
            return new ResponseEntity(list, HttpStatus.OK);
        }
    }
}
