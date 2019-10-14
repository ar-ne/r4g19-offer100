package r4g19.offer100.service.cym;

import org.springframework.stereotype.Service;
import r4g19.offer100.jooq.tables.daos.EntrepreneurialDao;
import r4g19.offer100.jooq.tables.daos.HiringDao;
import r4g19.offer100.jooq.tables.pojos.Entrepreneurial;
import r4g19.offer100.jooq.tables.pojos.Hiring;
import r4g19.offer100.service.ServiceBase;

@Service
public class HiringService extends ServiceBase {
    public Entrepreneurial getEntrepreneurial(Hiring hiring) {
        return new EntrepreneurialDao(dsl.configuration()).fetchOneByUsername(hiring.getUsername());
    }

    public Hiring getHiring(Long id) {
        return new HiringDao(dsl.configuration()).fetchById(id).get(0);
    }
}
