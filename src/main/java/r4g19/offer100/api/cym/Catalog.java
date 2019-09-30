package r4g19.offer100.api.cym;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import r4g19.offer100.api.APIBase;
import r4g19.offer100.model.cym.EmptyEntityModel;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping("api")
public class Catalog extends APIBase {
    @GetMapping("")
    public HttpEntity<EmptyEntityModel> index(HttpServletRequest request) {
        EmptyEntityModel entityModel = new EmptyEntityModel();
        if (request.getRemoteHost().equals("0:0:0:0:0:0:0:1") || request.getRemoteHost().equals("127.0.0.1")) {
//            entityModel.add(linkTo(Admin.class).withRel("admin"));
            entityModel.add(linkTo(Dev.class).withRel("dev"));
        }
        entityModel.add(linkTo(User.class).withRel("user"));
        entityModel.add(linkTo(Public.class).withRel("public"));
        entityModel.add(linkTo(Catalog.class).withSelfRel());
        return new ResponseEntity<>(entityModel, HttpStatus.OK);
    }
}
