package r4g19.offer100.api.cym;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import r4g19.offer100.annotations.cym.APIEntrance;
import r4g19.offer100.api.APIBase;
import r4g19.offer100.model.cym.EmptyEntityModel;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static r4g19.offer100.properties.cym.Vars.APIEntrances;

@RestController
@RequestMapping("api")
@APIEntrance(name = "index")
public class Catalog extends APIBase {

    @GetMapping("")
    public HttpEntity<EmptyEntityModel> index(HttpServletRequest request) {
        EmptyEntityModel entityModel = new EmptyEntityModel();
        for (Class<? extends APIBase> entrance : APIEntrances) {
            APIEntrance annotation = entrance.getAnnotation(APIEntrance.class);
            if (annotation.localOnly()) {
                if (request.getRemoteHost().equals("0:0:0:0:0:0:0:1") || request.getRemoteHost().equals("127.0.0.1"))
                    entityModel.add(linkTo(entrance).withRel(annotation.name()));
            } else entityModel.add(linkTo(entrance).withRel(annotation.name()));
        }
        return new ResponseEntity<>(entityModel, HttpStatus.OK);
    }
}
