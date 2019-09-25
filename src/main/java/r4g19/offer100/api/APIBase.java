package r4g19.offer100.api;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import r4g19.offer100.ComponentBase;

/**
 * 所有API的父类
 */
public abstract class APIBase extends ComponentBase {
    @Autowired
    DSLContext dsl;  //数据库连接信息
}
