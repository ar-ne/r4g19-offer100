package r4g19.offer100.properties.cym;

import org.jooq.Table;
import org.springframework.hateoas.RepresentationModel;
import r4g19.offer100.jooq.Public;
import r4g19.offer100.jooq.Tables;
import r4g19.offer100.properties.cym.mapping.UserType;

import java.lang.reflect.Constructor;
import java.util.*;

import static java.util.Collections.unmodifiableMap;
import static java.util.Collections.unmodifiableSet;

/**
 * 常量
 */
public class Vars {
    public static final String liveUpdate = "NX";
    public static final Set<String> PUBLIC_PAGES;
    public static final Map<Class, Constructor> POJO_DAO_MAPPER;
    public static final Map<Class, Table<?>> POJO_DAO_TABLE;
    public static final Map<UserType, Class<? extends RepresentationModel>> TYPE_POJO_MAP;
    public static final String JOOQ_PACKAGE_NAME;
//    public static final Map<CRUDOperation, Map<Class, MessageParam[]>> DB_BROADCAST_TRIGGER;


    /**
     * 填充数据
     */
    static {
        JOOQ_PACKAGE_NAME = Tables.class.getPackageName();
        String[] strings = new String[]{"notification", "profile", "production", "answerTicket"};
        PUBLIC_PAGES = unmodifiableSet(new HashSet<>(Arrays.asList(strings)));

        HashMap<Class, Constructor> map = new HashMap<>();
        HashMap<Class, Table<?>> map1 = new HashMap<>();
        System.out.println("Building static map => Vars.POJO_DAO_MAPPER | Vars.POJO_DAO_MAPPER");
        for (Table<?> table : Public.PUBLIC.getTables()) {
            String pojo = String.format("%s.pojos.%s", table.getClass().getPackage().getName(), table.getClass().getSimpleName());
            String dao = String.format("%s.daos.%sDao", table.getClass().getPackage().getName(), table.getClass().getSimpleName());
            System.out.println(pojo + " <=> " + dao);
            try {
                map.put(Class.forName(pojo), Class.forName(dao).getConstructor(org.jooq.Configuration.class));
                map1.put(Class.forName(pojo), table);
            } catch (ClassNotFoundException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        POJO_DAO_MAPPER = unmodifiableMap(map);
        POJO_DAO_TABLE = unmodifiableMap(map1);
        System.out.println("Built Vars.POJO_DAO_MAPPER...............");

        HashMap<UserType, Class<? extends RepresentationModel>> map2 = new HashMap<>();
        map2.put(UserType.Personal, r4g19.offer100.jooq.tables.pojos.Personal.class);

        map2.put(UserType.Entrepreneurial, r4g19.offer100.jooq.tables.pojos.Entrepreneurial.class);
        TYPE_POJO_MAP = Collections.unmodifiableMap(map2);
    }
}
