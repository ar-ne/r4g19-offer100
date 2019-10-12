package r4g19.offer100.service.cym;

import org.jooq.impl.DAOImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import r4g19.offer100.jooq.tables.pojos.Log;
import r4g19.offer100.properties.cym.CRUDOperation;
import r4g19.offer100.service.ServiceBase;
import r4g19.offer100.utils.cym.LogMsgHelper;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import static r4g19.offer100.properties.cym.Vars.POJO_DAO_MAPPER;
import static r4g19.offer100.properties.cym.Vars.POJO_DAO_TABLE;

/**
 * 常用的增删改查
 */
@Service
public class CommonCRUD extends ServiceBase {

    @SuppressWarnings("unchecked")
    private <T> T getUpdatedObject(T o) {
        try {
            Class clazz = o.getClass();

            Object userObj = clazz.getConstructor().newInstance();  //创建一个对应用户类型的实例
            Object dbObj = POJO_DAO_MAPPER.get(clazz); //从数据库获取用户的原始信息

            for (Field field : clazz.getDeclaredFields()) { //遍历类属性，包括私有属性
                boolean dboAcc = field.canAccess(dbObj);  //获取属性原本是否可写
                field.setAccessible(true); //设置可写
                if (field.get(userObj) != null)
                    field.set(dbObj, field.get(userObj));  //更新类属性
                field.setAccessible(dboAcc); //还原是否可写
            }
            return (T) dbObj;
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return o;
    }

    public <T> T newRecord(T o, String username) {
        return doOperation(o, username, CRUDOperation.INSERT);
    }

    public <T> T updateRecord(T o, String username) {
        return doOperation(o, username, CRUDOperation.UPDATE);
    }

    @SuppressWarnings("unchecked")
    @Transactional
    <T> T doOperation(T o, String username, CRUDOperation operation) {
        try {
            Class c = o.getClass();
            switch (operation) {
                case INSERT:
                    ((DAOImpl) POJO_DAO_MAPPER.get(c).newInstance(dsl.configuration())).insert(o);
                    break;
                case UPDATE:
                    ((DAOImpl) POJO_DAO_MAPPER.get(c).newInstance(dsl.configuration())).update(getUpdatedObject(o));
                    break;
                case DELETE:
                    ((DAOImpl) POJO_DAO_MAPPER.get(c).newInstance(dsl.configuration())).delete(o);
                    break;
                case GET:
                    ((DAOImpl) POJO_DAO_MAPPER.get(c).newInstance(dsl.configuration())).findAll();
            }
            Log log = LogMsgHelper.CRUD.newOperation(POJO_DAO_TABLE.get(c), username, operation, o);
            dbLogger.log(log);
//            notification.dbTrigger(operation, o, username);
            return o;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}
