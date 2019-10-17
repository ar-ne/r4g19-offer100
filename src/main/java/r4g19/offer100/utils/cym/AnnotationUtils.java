package r4g19.offer100.utils.cym;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.*;

public class AnnotationUtils {
    private static final String ANNOTATION_DATA = "annotationData";
    private static final String ANNOTATION_FIELDS = "declaredAnnotations";
    private static final String ANNOTATIONS = "annotations";

    /**
     * 高耗时操作，请勿频繁调用
     *
     * @param annotationType annotationType
     * @param basePackage    basePackage
     * @return Set<Class> which class will have the annotation
     */
    @Deprecated
    public static Set<Class> findAllClassesWithAnnotation(Class<? extends Annotation> annotationType, String basePackage) {
        LoggerFactory.getLogger(AnnotationUtils.class).trace("findAllClassesWithAnnotation:annotationType={},basePackage={}", annotationType, basePackage);
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

    @SuppressWarnings("unchecked")
    public static void alertAnnotationAtRuntime(Executable target, Class<? extends Annotation> annotationType, Map values) {
        try {
            LoggerFactory.getLogger(AnnotationUtils.class).trace("alertAnnotationAtRuntime:target={},annotation={}", target, annotationType);
//            Method method = Method.class.getDeclaredMethod(ANNOTATION_DATA, (Class<?>[]) null);
//            method.setAccessible(true);
//            Object annotationData = method.invoke(target);
//            Field annotations = target.getClass().getDeclaredField(ANNOTATION_FIELDS);
//            annotations.setAccessible(true);
//            Map map = (Map) annotations.get(annotations);
            Constructor<?> annotationInvocationHandler = Class.forName("sun.reflect.annotation.AnnotationInvocationHandler").getDeclaredConstructor(Class.class, Map.class);
            annotationInvocationHandler.setAccessible(true);
            addAnnotation(target, (Annotation) Proxy.newProxyInstance(annotationType.getClassLoader(), new Class[]{annotationType}, (InvocationHandler) annotationInvocationHandler.newInstance(annotationType, values)));
//            map.put(annotationType, Proxy.newProxyInstance(annotationType.getClassLoader(), new Class[]{annotationType}, (InvocationHandler) annotationInvocationHandler.newInstance(annotationType, values)));

        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | NoSuchFieldException | ClassNotFoundException | InstantiationException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public static void alertAnnotationAtRuntime(Class target, Class<? extends Annotation> annotationType, Map values) {
        try {
            LoggerFactory.getLogger(AnnotationUtils.class).trace("alertAnnotationToClassAtRuntime:target={},annotation={}", target, annotationType);
            target.getAnnotation(Annotation.class);
            Method method = Class.class.getDeclaredMethod(ANNOTATION_DATA, (Class<?>[]) null);
            method.setAccessible(true);
            Object annotationData = method.invoke(target);
            Field annotations = annotationData.getClass().getDeclaredField(ANNOTATIONS);
            annotations.setAccessible(true);
            Map map = (Map) annotations.get(annotationData);
            Constructor<?> annotationInvocationHandler = Class.forName("sun.reflect.annotation.AnnotationInvocationHandler").getDeclaredConstructor(Class.class, Map.class);
            annotationInvocationHandler.setAccessible(true);
            map.put(annotationType, Proxy.newProxyInstance(annotationType.getClassLoader(), new Class[]{annotationType}, (InvocationHandler) annotationInvocationHandler.newInstance(annotationType, values)));
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | NoSuchFieldException | ClassNotFoundException | InstantiationException e) {
            e.printStackTrace();
        }
    }

    /**
     * Add annotation to Executable(Method or Constructor)<br>
     * Note that you may need to give the root method.
     *
     * @param ex
     * @param annotation
     * @author XDean
     * @see Executable
     */
    @SuppressWarnings("unchecked")
    public static void addAnnotation(Executable ex, Annotation annotation) throws NoSuchFieldException, IllegalAccessException {
        ex.getAnnotation(Annotation.class);// prevent declaredAnnotations haven't initialized
        Field Field_Excutable_DeclaredAnnotations = Executable.class.getDeclaredField(ANNOTATION_FIELDS);
        Field_Excutable_DeclaredAnnotations.setAccessible(true);
        Map<Class<? extends Annotation>, Annotation> annos = (Map<Class<? extends Annotation>, Annotation>) Field_Excutable_DeclaredAnnotations.get(ex);
        if (annos.getClass() == Collections.EMPTY_MAP.getClass()) {
            annos = new HashMap<>();
            Field_Excutable_DeclaredAnnotations.set(ex, annos);
        }
        annos.put(annotation.annotationType(), annotation);
    }

}
