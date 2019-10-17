package r4g19.offer100.service.zhj;

import org.springframework.stereotype.Service;
import r4g19.offer100.jooq.tables.daos.HiringDao;
import r4g19.offer100.jooq.tables.pojos.Hiring;
import r4g19.offer100.service.ServiceBase;

@Service
public class TestService extends ServiceBase {
    public HiringDao getHiringDao(){
        return new HiringDao(dsl.configuration());
    }
}
