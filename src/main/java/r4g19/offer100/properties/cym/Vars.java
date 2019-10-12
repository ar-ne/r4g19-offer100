package r4g19.offer100.properties.cym;

import org.jooq.Table;
import org.springframework.hateoas.RepresentationModel;
import r4g19.offer100.annotations.cym.APIEntrance;
import r4g19.offer100.api.APIBase;
import r4g19.offer100.jooq.Public;
import r4g19.offer100.jooq.Tables;
import r4g19.offer100.properties.cym.mapping.UserType;
import r4g19.offer100.utils.cym.AnnotationUtils;

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
    public static final Set<Class<? extends APIBase>> API_ENTRANCES;
    public static Vars instance = null;

    /**
     * 填充数据
     */
    static {
        System.out.println("Initializing static variables...");
        {
            JOOQ_PACKAGE_NAME = Tables.class.getPackageName();
            System.out.println("JOOQ_PACKAGE_NAME=" + JOOQ_PACKAGE_NAME);
        }
        {
            String[] strings = new String[]{"notification", "profile", "production", "answerTicket", "hiring"};
            PUBLIC_PAGES = unmodifiableSet(new HashSet<>(Arrays.asList(strings)));
            System.out.println("PUBLIC_PAGES=" + Arrays.toString(PUBLIC_PAGES.toArray()));
        }
        {
            HashMap<Class, Constructor> map = new HashMap<>();
            HashMap<Class, Table<?>> map1 = new HashMap<>();
            System.out.println("Building static maps: Vars.POJO_DAO_MAPPER , Vars.POJO_DAO_MAPPER");
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
            System.out.println("Build static maps: Vars.POJO_DAO_MAPPER , Vars.POJO_DAO_MAPPER...DONE");
            System.out.println("POJO_DAO_MAPPER=" + Arrays.toString(POJO_DAO_MAPPER.entrySet().toArray()));
            System.out.println("POJO_DAO_TABLE=" + Arrays.toString(POJO_DAO_TABLE.entrySet().toArray()));
        }
        {
            HashMap<UserType, Class<? extends RepresentationModel>> map2 = new HashMap<>();
            map2.put(UserType.Personal, r4g19.offer100.jooq.tables.pojos.Personal.class);
            map2.put(UserType.Entrepreneurial, r4g19.offer100.jooq.tables.pojos.Entrepreneurial.class);
            TYPE_POJO_MAP = unmodifiableMap(map2);
            System.out.println("TYPE_POJO_MAP=" + Arrays.toString(TYPE_POJO_MAP.entrySet().toArray()));
        }
        {
            Set<Class> tmpEntrance = AnnotationUtils.findAllClassesWithAnnotation(APIEntrance.class, "r4g19.offer100.api");
            Set<Class<? extends APIBase>> tmpE2 = new HashSet<>();
            for (Class aClass : tmpEntrance) {
                if (aClass.getSuperclass().equals(APIBase.class))
                    tmpE2.add(aClass);
            }
            API_ENTRANCES = unmodifiableSet(tmpE2);
            System.out.println("API_ENTRANCES=" + Arrays.toString(API_ENTRANCES.toArray()));
        }
        System.out.println("Initializing static variables...DONE");
    }


    private Vars() {
    }

    public synchronized static Vars getInstance() {
        if (instance == null)
            instance = new Vars();
        return instance;
    }
}
