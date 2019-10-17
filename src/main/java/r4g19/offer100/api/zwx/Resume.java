package r4g19.offer100.api.zwx;

import r4g19.offer100.annotations.cym.API;
import r4g19.offer100.api.APIBase;
import r4g19.offer100.jooq.tables.daos.ResumeDao;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;
import java.util.List;

public class Resume extends APIBase {


    @API("user/personal")
    public static class Personal extends APIBase {
        @GET
        @Path("resume/list")
        public List resumeList(@Context SecurityContext securityContext) {
            return new ResumeDao(dsl.configuration()).fetchByUsername(Resume.getUsername(securityContext));
        }
    }
}
