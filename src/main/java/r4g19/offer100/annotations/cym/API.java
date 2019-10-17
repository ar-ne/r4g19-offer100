package r4g19.offer100.annotations.cym;

import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.*;

@Documented
@RestController
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface API {
    String value();

    String rel() default "";

    boolean localOnly() default false;
}
