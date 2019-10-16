package r4g19.offer100.api.cym;

import r4g19.offer100.annotations.cym.API;
import r4g19.offer100.api.APIBase;
import r4g19.offer100.jooq.tables.daos.HiringDao;
import r4g19.offer100.jooq.tables.daos.SubmissionDao;
import r4g19.offer100.jooq.tables.pojos.Submission;
import r4g19.offer100.properties.cym.mapping.SubmissionStatus;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;
import java.util.Arrays;
import java.util.List;

@API("user")
public class User extends APIBase {
    @API("user/entrepreneurial")
    public static class Entrepreneurial extends APIBase {
        @GET
        @Path("hiring/list")
        public List hiringList(@Context SecurityContext securityContext) {
            return new HiringDao(dsl.configuration()).fetchByUsername(User.getUsername(securityContext));
        }

        @GET
        @Path("submission/list")
        public List submissionList(@Context SecurityContext securityContext) {
            return new SubmissionDao(dsl.configuration()).fetchByHirUsername(getUsername(securityContext));
        }

        @POST
        @Path("submission/{operate}")
        public String submissionOperate(Submission[] submissions, @PathParam("operate") SubmissionStatus operate) {
            for (Submission submission : submissions) {
                submission.setStatus(operate);
            }
            new SubmissionDao(dsl.configuration()).update(Arrays.asList(submissions));
            return "success";
        }
    }
}
