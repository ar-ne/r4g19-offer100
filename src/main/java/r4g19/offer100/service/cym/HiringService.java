package r4g19.offer100.service.cym;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import r4g19.offer100.jooq.tables.daos.HiringDao;
import r4g19.offer100.jooq.tables.pojos.Entrepreneurial;
import r4g19.offer100.jooq.tables.pojos.Hiring;
import r4g19.offer100.service.ServiceBase;

@Service
public class HiringService extends ServiceBase {
    @Autowired
    EntrepreneurialService entrepreneurialService;

    public Entrepreneurial getEntrepreneurial(Hiring hiring) {
        return entrepreneurialService.getEntrepreneurial(hiring.getUsername());
    }

    public Hiring getHiring(Long id) {
        return new HiringDao(dsl.configuration()).fetchById(id).get(0);
    }

    public void delete(Long id) {
        new HiringDao(dsl.configuration()).fetchById(id);
    }
}
