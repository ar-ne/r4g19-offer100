package r4g19.offer100.api.cym;

import org.jooq.Field;
import org.jooq.Table;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import r4g19.offer100.model.EmptyEntity;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static r4g19.offer100.jooq.Public.PUBLIC;

@RestController
@RequestMapping("/api/admin/dev/info")
public class DevInfo {
    @GetMapping("/")
    public HttpEntity index() {
        EmptyEntity emptyEntity = new EmptyEntity();
        emptyEntity.add(linkTo(this.getClass()).withSelfRel());
        emptyEntity.add(linkTo(methodOn(DevInfo.class).table()).withSelfRel());
        return new ResponseEntity(emptyEntity, HttpStatus.OK);
    }

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
