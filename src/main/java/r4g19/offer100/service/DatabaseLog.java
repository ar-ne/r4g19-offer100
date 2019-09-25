package r4g19.offer100.service;

import org.jooq.DSLContext;
import org.springframework.stereotype.Service;
import r4g19.offer100.jooq.tables.daos.LogDao;
import r4g19.offer100.jooq.tables.pojos.Log;

import java.sql.Timestamp;

/**
 * 数据库日志
 */
@Service
public class DatabaseLog {
    private final LogDao dao;

    public DatabaseLog(DSLContext dsl) {
        this.dao = new LogDao(dsl.configuration());
    }

    public void log(String loginName, String operation, String result) {
        Log log = new Log();
        log.setUsername(loginName);
        log.setDescription(operation);
        log.setNotes(result);
        log.setTime(new Timestamp(System.currentTimeMillis()));
        dao.insert(log);
    }

    public void log(String message) {
        Log log = new Log();
        log.setDescription(message);
        log.setTime(new Timestamp(System.currentTimeMillis()));
        dao.insert(log);
    }

    public void log(Log log) {
        dao.insert(log);
    }
}
