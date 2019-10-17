package r4g19.offer100;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit4.SpringRunner;
import r4g19.offer100.annotations.cym.API;
import r4g19.offer100.api.APIBase;
import r4g19.offer100.controller.ControllerBase;
import r4g19.offer100.properties.cym.Vars;
import r4g19.offer100.service.ServiceBase;
import r4g19.offer100.utils.cym.AnnotationUtils;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

    @Autowired
    DataSource dataSource;

    @Test
    public void contextLoads() throws SQLException {
        System.out.println(dataSource.getClass());

        Connection connection = dataSource.getConnection();
        System.out.println(connection);
        connection.close();
    }

    @Test
    public void VarsTest() throws IllegalAccessException {
        Class clazz = Vars.class;
        Vars vars = new Vars();
        for (Field declaredField : clazz.getDeclaredFields()) {
            Assert.assertNotNull(declaredField.get(vars));
        }
    }

    @Test
    public void API_Test() {
        for (Class<? extends APIBase> aClass : Vars.API) {
            Assert.assertNotNull(aClass.getAnnotation(API.class));
        }
    }

    @Test
    public void SuperClassTest() {
        System.out.printf("%s <=> %s", API.class.getName(), APIBase.class.getName());
        for (Class clazz : AnnotationUtils.findAllClassesWithAnnotation(API.class, "r4g19.offer100.api"))
            Assert.assertEquals(clazz.getSuperclass(), APIBase.class);

        System.out.printf("%s <=> %s", Controller.class.getName(), ControllerBase.class.getName());
        for (Class clazz : AnnotationUtils.findAllClassesWithAnnotation(API.class, "r4g19.offer100.controller"))
            Assert.assertEquals(clazz.getSuperclass(), ControllerBase.class);

        System.out.printf("%s <=> %s", Service.class.getName(), ServiceBase.class.getName());
        for (Class clazz : AnnotationUtils.findAllClassesWithAnnotation(API.class, "r4g19.offer100.service"))
            Assert.assertEquals(clazz.getSuperclass(), ServiceBase.class);
    }

}
