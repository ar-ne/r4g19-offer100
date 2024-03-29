package r4g19.offer100.service.cym;

import org.jooq.impl.DAOImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import r4g19.offer100.jooq.tables.pojos.Log;
import r4g19.offer100.properties.cym.CRUDOperation;
import r4g19.offer100.service.ServiceBase;
import r4g19.offer100.utils.cym.LogMsgHelper;

import java.lang.reflect.InvocationTargetException;

import static r4g19.offer100.properties.cym.Vars.POJO_DAO_MAPPER;
import static r4g19.offer100.properties.cym.Vars.POJO_TABLE_MAPPER;

/**
 * 常用的增删改查
 */
@Service
public class CommonCRUD extends ServiceBase {

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
                    ((DAOImpl) POJO_DAO_MAPPER.get(c).newInstance(dsl.configuration())).update(o);
                    break;
                case DELETE:
                    ((DAOImpl) POJO_DAO_MAPPER.get(c).newInstance(dsl.configuration())).delete(o);
                    break;
                case GET:
                    ((DAOImpl) POJO_DAO_MAPPER.get(c).newInstance(dsl.configuration())).findAll();
            }
            Log log = LogMsgHelper.CRUD.newOperation(POJO_TABLE_MAPPER.get(c), username, operation, o);
            dbLogger.log(log);
//            notification.dbTrigger(operation, o, username);
            return o;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}
