package r4g19.offer100.service;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import r4g19.offer100.ComponentBase;

/**
 * 所有服务的父类
 */
@Service
public abstract class ServiceBase extends ComponentBase {
    @Autowired
    protected DSLContext dsl;
//    @Autowired
//    protected NotificationService notification;
}
