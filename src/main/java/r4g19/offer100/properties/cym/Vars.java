package r4g19.offer100.properties.cym;

import org.jooq.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import r4g19.offer100.annotations.cym.API;
import r4g19.offer100.api.APIBase;
import r4g19.offer100.jooq.Public;
import r4g19.offer100.jooq.Tables;
import r4g19.offer100.properties.cym.mapping.UserType;
import r4g19.offer100.utils.cym.AnnotationUtils;

import javax.ws.rs.core.MediaType;
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
    public static final Map<Class, Table<?>> POJO_TABLE_MAPPER;
    public static final Map<UserType, Class> TYPE_POJO_MAP;
    public static final String JOOQ_PACKAGE_NAME;
    public static final Set<Class<? extends APIBase>> API;
    public static final Set<Class<? extends APIBase>> API_INNER_CLASS;
    public static final Map MEDIA_TYPE;

    /**
     * 填充数据
     */
    static {
        Logger logger = LoggerFactory.getLogger(Vars.class);
        {
            MEDIA_TYPE = Map.of("value", new String[]{MediaType.APPLICATION_JSON});
        }
        logger.debug("Initializing static variables...");
        {
            JOOQ_PACKAGE_NAME = Tables.class.getPackageName();
            logger.trace("JOOQ_PACKAGE_NAME={}", JOOQ_PACKAGE_NAME);
        }
        {
            String[] strings = new String[]{"notification", "profile", "production", "answerTicket", "hiring"};
            PUBLIC_PAGES = unmodifiableSet(new HashSet<>(Arrays.asList(strings)));
            logger.trace("PUBLIC_PAGES={}", Arrays.toString(PUBLIC_PAGES.toArray()));
        }
        {
            HashMap<Class, Constructor> map = new HashMap<>();
            HashMap<Class, Table<?>> map1 = new HashMap<>();
            logger.trace("Building static maps: Vars.POJO_DAO_MAPPER , Vars.POJO_DAO_MAPPER");
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
            POJO_TABLE_MAPPER = unmodifiableMap(map1);
            logger.trace("Build static maps: Vars.POJO_DAO_MAPPER , Vars.POJO_DAO_MAPPER...DONE");
            logger.trace("POJO_DAO_MAPPER={}", Arrays.toString(POJO_DAO_MAPPER.entrySet().toArray()));
            logger.trace("POJO_TABLE_MAPPER={}", Arrays.toString(POJO_TABLE_MAPPER.entrySet().toArray()));
        }
        {
            HashMap<UserType, Class> map2 = new HashMap<>();
            map2.put(UserType.Personal, r4g19.offer100.jooq.tables.pojos.Personal.class);
            map2.put(UserType.Entrepreneurial, r4g19.offer100.jooq.tables.pojos.Entrepreneurial.class);
            TYPE_POJO_MAP = unmodifiableMap(map2);
            logger.trace("TYPE_POJO_MAP={}", Arrays.toString(TYPE_POJO_MAP.entrySet().toArray()));
        }
        {
            Set<Class> tmpEntrance = AnnotationUtils.findAllClassesWithAnnotation(API.class, "r4g19.offer100.api");
            Set innerClass = new HashSet<>();
            Set<Class<? extends APIBase>> tmpE2 = new HashSet<>();
            for (Class aClass : tmpEntrance) {
                if (aClass.getSuperclass().equals(APIBase.class)) {
                    innerClass.addAll(Arrays.asList(aClass.getDeclaredClasses()));
                    tmpE2.add(aClass);
                }
            }
            API = unmodifiableSet(tmpE2);
            API_INNER_CLASS = unmodifiableSet(innerClass);
            logger.trace("API={}", Arrays.toString(API.toArray()));
        }
        logger.debug("Initializing static variables...DONE");
    }
}
