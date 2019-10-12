package r4g19.offer100.api;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import r4g19.offer100.ComponentBase;
import r4g19.offer100.model.cym.EmptyEntityModel;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashSet;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

/**
 * 所有API的父类
 */
@RestController
public abstract class APIBase extends ComponentBase {
    @Autowired
    protected DSLContext dsl;  //数据库连接信息

    @GetMapping("")
    public HttpEntity<EmptyEntityModel> index(HttpServletRequest request) {
        EmptyEntityModel entity = new EmptyEntityModel();
        entity.add(getAllLinkWithinClass(this.getClass()));
        return new ResponseEntity<>(entity, HttpStatus.OK);
    }

    @SuppressWarnings("unchecked")
    public Collection<Link> getAllLinkWithinClass(Class<? extends APIBase> clazz) {
        Collection<Link> links = new HashSet<>();
        links.add(linkTo(clazz).withSelfRel());
        links.addAll(getAllMethodLinkWithRel(clazz, "methods"));
        Class<?>[] innerClass = clazz.getDeclaredClasses();
        for (Class<?> aClass : innerClass) {
            if (aClass.getSuperclass().equals(APIBase.class)) {
                links.add(linkTo(aClass).withRel(aClass.getSimpleName().toLowerCase()));
//                links.addAll(getAllMethodLinkWithRel((Class<? extends APIBase>) aClass, aClass.getSimpleName().toLowerCase()));
            }
        }
        return links;
    }

    public Collection<Link> getAllMethodLinkWithRel(Class<? extends APIBase> clazz, String rel) {
        Collection<Link> links = new HashSet<>();
        for (Method declaredMethod : clazz.getDeclaredMethods()) {
            links.add(linkTo(declaredMethod, "").withRel(rel));
        }
        return links;
    }
}
