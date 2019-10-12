package r4g19.offer100.utils.cym;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

public class AnnotationUtils {
    /**
     * 高耗时操作，请勿频繁调用
     *
     * @param annotationType annotationType
     * @param basePackage    basePackage
     * @return Set<Class> which class will have the annotation
     */
    @Deprecated
    public static Set<Class> findAllClassesWithAnnotation(Class<? extends Annotation> annotationType, String basePackage) {
        ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
        provider.addIncludeFilter(new AnnotationTypeFilter(annotationType));
        HashSet<Class> classes = new HashSet<>();
        for (BeanDefinition beanDefinition : provider.findCandidateComponents(basePackage)) {
            try {
                classes.add(Class.forName(beanDefinition.getBeanClassName()));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return classes;
    }
}
