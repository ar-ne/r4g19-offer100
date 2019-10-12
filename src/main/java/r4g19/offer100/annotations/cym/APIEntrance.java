package r4g19.offer100.annotations.cym;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface APIEntrance {
    String name() default "";

    boolean localOnly() default false;
}
