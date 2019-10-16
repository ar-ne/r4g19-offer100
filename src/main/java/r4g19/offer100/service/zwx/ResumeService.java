package r4g19.offer100.service.zwx;

import org.springframework.stereotype.Service;
import r4g19.offer100.jooq.tables.daos.PersonalDao;
import r4g19.offer100.jooq.tables.daos.ResumeDao;
import r4g19.offer100.jooq.tables.pojos.Personal;
import r4g19.offer100.jooq.tables.pojos.Resume;
import r4g19.offer100.service.ServiceBase;

@Service
public class ResumeService extends ServiceBase {
    public Resume getResume(Long id) {
        return new ResumeDao(dsl.configuration()).fetchById(id).get(0);
    }

    public Personal getPersonal(Resume resume) {
        return new PersonalDao(dsl.configuration()).fetchOneByUsername(resume.getUsername());
    }

}
