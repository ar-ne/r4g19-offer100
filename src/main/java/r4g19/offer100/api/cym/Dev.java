package r4g19.offer100.api.cym;

import org.jooq.Table;
import org.jooq.impl.DAOImpl;
import r4g19.offer100.annotations.cym.API;
import r4g19.offer100.api.APIBase;
import r4g19.offer100.jooq.tables.pojos.Login;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import static r4g19.offer100.jooq.Public.PUBLIC;
import static r4g19.offer100.properties.cym.Vars.*;

@API(value = "dev", localOnly = true)
public class Dev extends APIBase {

    @API("dev/data")
    public static class Data extends APIBase {
        @GET
        @Path("tables/{t}")
        public List tableSample(@PathParam("t") String t) {
            try {
                Class table = Class.forName(JOOQ_PACKAGE_NAME + ".tables.pojos." + t);
                List d = ((DAOImpl) POJO_DAO_MAPPER.get(table).newInstance(dsl.configuration())).findAll();
                return d;
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
            throw new RuntimeException("NOT_FOUND");
        }
    }

    @API("dev/sample")
    public static class Sample extends APIBase {
        @GET
        @Path("tables/{t}")
        public List<Login> tableSample(@PathParam("t") String t) {
            List<Login> logins = new ArrayList<>();
            for (int i = 0; i < 100; i++) {
                logins.add(new Login());
            }
            return logins;
        }
    }

    @API("dev/info")
    public static class Info extends APIBase {
        @GET
        @Path("/tables")
        public List<String> visibility() {
            List<String> list = new ArrayList<>();
            for (Table<?> table : PUBLIC.getTables()) {
                for (org.jooq.Field<?> field : table.fields()) {
                    list.add(table.getName() + "." + field.getName().toLowerCase());
                }
            }
            return list;
        }

        @GET
        @Path("/bs-tables")
        public List<String> translate() {
            List<String> list = new ArrayList<>();
            for (Table<?> table : PUBLIC.getTables()) {
                Class t = TABLE_POJO_MAPPER.get(table);
                for (Field field : t.getDeclaredFields()) {
                    if (field.getName().equalsIgnoreCase("serialVersionUID")) continue;
                    list.add(table.getName() + "." + field.getName());
                }
            }
            return list;
        }
    }
}
