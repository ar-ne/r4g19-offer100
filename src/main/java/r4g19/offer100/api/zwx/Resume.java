package r4g19.offer100.api.zwx;

import r4g19.offer100.annotations.cym.API;
import r4g19.offer100.api.APIBase;
import r4g19.offer100.jooq.tables.daos.ResumeDao;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;
import java.util.List;

@API("user/personal/resume")
public class Resume extends APIBase {
    @GET
    @Path("list")
    public List resumeList(@Context SecurityContext securityContext) {
        return new ResumeDao(dsl.configuration()).fetchByUsername(Resume.getUsername(securityContext));
    }
}
