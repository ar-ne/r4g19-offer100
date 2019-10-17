package r4g19.offer100.api;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RestController;
import r4g19.offer100.ComponentBase;
import r4g19.offer100.properties.cym.mapping.UserType;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;


/**
 * 所有API的父类
 */
@RestController
public abstract class APIBase extends ComponentBase {
    @Autowired
    protected DSLContext dsl;  //数据库连接信息

//    @SuppressWarnings("unchecked")
//    public static Collection<Link> getAllLinkWithinClass(Class<? extends APIBase> clazz, UriInfo uriInfo) {
//        Collection<Link> links = new HashSet<>();
//        links.add(fromUriBuilder(uriInfo.getAbsolutePathBuilder()).rel("_self").build());
//        links.addAll(getAllMethodLinkWithRel(clazz, "methods", uriInfo));
//        Class<?>[] innerClass = clazz.getDeclaredClasses();
//        for (Class<?> aClass : innerClass) {
//            if (aClass.getSuperclass().equals(APIBase.class)) {
//                links.add(fromUriBuilder(uriInfo.getAbsolutePathBuilder()).rel(aClass.getSimpleName().toLowerCase()).build());
////                links.addAll(getAllMethodLinkWithRel((Class<? extends APIBase>) aClass, aClass.getSimpleName().toLowerCase()));
//            }
//        }
//        return links;
//    }
//
//    public static Collection<Link> getAllMethodLinkWithRel(Class<? extends APIBase> clazz, String rel, UriInfo uriInfo) {
//        Collection<Link> links = new HashSet<>();
//        for (Method declaredMethod : clazz.getDeclaredMethods()) {
//            links.add(fromUriBuilder(uriInfo.getAbsolutePathBuilder()).rel(rel).build());
//        }
//        return links;
//    }

    /**
     * 获取登录名
     *
     * @param context 按照JAX-RS规范获取登录名
     * @return 登录名
     */
    public static String getUsername(SecurityContext context) {
        return getUsername(getAuthentication(context));
    }

    public static UserType getUserType(SecurityContext context) {
        return getUserType(getAuthentication(context));
    }

    public static Authentication getAuthentication(SecurityContext context) {
        return (UsernamePasswordAuthenticationToken) context.getUserPrincipal();
    }

    @GET
    public Response index(HttpServletRequest request, @Context UriInfo uriInfo) {
        return Response.ok(String.format("%s says Hello World!", this.getClass())).build();
    }

}
