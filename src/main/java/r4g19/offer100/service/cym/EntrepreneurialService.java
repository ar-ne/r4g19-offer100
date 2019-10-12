package r4g19.offer100.service.cym;

import org.springframework.stereotype.Service;
import r4g19.offer100.jooq.tables.daos.EntrepreneurialDao;
import r4g19.offer100.jooq.tables.pojos.Entrepreneurial;
import r4g19.offer100.service.ServiceBase;

@Service
public class EntrepreneurialService extends ServiceBase {
    public Entrepreneurial getEntrepreneurial(String username) {
        return new EntrepreneurialDao(dsl.configuration()).fetchOneByUsername(username);
    }
}
