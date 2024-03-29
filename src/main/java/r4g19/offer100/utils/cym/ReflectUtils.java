package r4g19.offer100.utils.cym;

import org.jooq.Table;
import r4g19.offer100.jooq.Tables;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import static r4g19.offer100.properties.cym.Vars.JOOQ_PACKAGE_NAME;

public class ReflectUtils {
    /**
     * 获取表，忽略大小写
     *
     * @return org.jooq.Table
     */
    public static Table getTable(String tableName) {
        try {
            return (Table) Class.forName(String.format("%s.tables.%s", JOOQ_PACKAGE_NAME, tableName)).getConstructor().newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException ignored) {
        }
        try {
            Class tables = Tables.class;
            for (Field field : tables.getDeclaredFields()) {
                if (field.getName().equalsIgnoreCase(tableName))
                    return (Table) field.getType().getConstructor().newInstance();
            }
        } catch (IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException ignored) {
        }

        throw new RuntimeException("Can't find the table with name " + tableName);
    }

    public static org.jooq.Field getField(String tableName, String fieldName) {
        return getField(getTable(tableName), fieldName);
    }

    public static org.jooq.Field getField(Table table, String fieldName) {
        for (Field field : table.getClass().getDeclaredFields()) {
            if (field.getName().equalsIgnoreCase(fieldName)) {
                try {
                    return (org.jooq.Field) field.get(table);
                } catch (IllegalAccessException ignored) {
                }
            }
        }
        throw new RuntimeException("Not impled");
    }

    public static <T> T mergeWithSuper(T current, Object super_) {
        Class curClazz = current.getClass().getSuperclass();
        Class superClazz = super_.getClass();
        if (!curClazz.equals(superClazz)) throw new RuntimeException("can not mergeWithSuper!");
        for (Field superField : superClazz.getDeclaredFields()) {
            if (superField.getName().equalsIgnoreCase("serialVersionUID")) continue;
            try {
                Field curField = curClazz.getDeclaredField(superField.getName());
                curField.setAccessible(true);
                superField.setAccessible(true);
                curField.set(current, superField.get(super_));
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return current;
    }
}
