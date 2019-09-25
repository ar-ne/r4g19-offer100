package r4g19.offer100.utils;

import org.jooq.Table;
import r4g19.offer100.jooq.tables.pojos.Log;
import r4g19.offer100.jooq.tables.pojos.Login;
import r4g19.offer100.properties.CRUDOperation;

import java.sql.Timestamp;

/**
 * 用于生成日志信息
 */
public class LogMsgHelper {
    public static Timestamp getTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }

    public static class Auth {
        public static Log newLogin(Login login) {
            Log log = new Log();
            log.setDescription(String.format("Add new login info=>loginName: %s, passwd: masked, type: %s", login.getUsername(), login.getType()));
            log.setTime(getTimestamp());
            return log;
        }

        public static Log loginAttempt(String name, boolean isAuthenticated) {
            Log log = new Log();
            log.setUsername(name);
            log.setNotes("isAuthenticated=" + isAuthenticated);
            log.setDescription("User login");
            log.setTime(getTimestamp());
            return log;
        }
    }

    public static class CRUD {
        public static Log newOperation(Table table, String loginName, CRUDOperation op, Object o) {
            Log log = new Log();
            log.setDescription(op.name() + " on table " + table.getName());
            log.setUsername(loginName);
            log.setNotes(o.toString());
            log.setTime(getTimestamp());
            return log;
        }
    }
}
