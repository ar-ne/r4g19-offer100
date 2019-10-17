package r4g19.offer100.api.cym;

import org.springframework.beans.factory.annotation.Autowired;
import r4g19.offer100.annotations.cym.API;
import r4g19.offer100.api.APIBase;
import r4g19.offer100.jooq.tables.daos.HiringDao;
import r4g19.offer100.jooq.tables.daos.SubmissionDao;
import r4g19.offer100.jooq.tables.pojos.Hiring;
import r4g19.offer100.jooq.tables.pojos.Resume;
import r4g19.offer100.jooq.tables.pojos.Submission;
import r4g19.offer100.properties.cym.mapping.SubmissionStatus;
import r4g19.offer100.service.cym.CommonCRUD;
import r4g19.offer100.service.cym.HiringService;
import r4g19.offer100.service.zhj.JobService;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;
import java.util.Arrays;
import java.util.List;

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

    @API("user/personal")
    public static class PersonalAPI_cym extends APIBase {
        @Autowired
        JobService jobService;
        @Autowired
        HiringService hiringService;
        @Autowired
        CommonCRUD crud;

        @PUT
        @Path("request/{hir_id}")
        public String request(@PathParam("hir_id") Long hir_id, Resume resume, @Context SecurityContext securityContext) {
            Hiring hiring = hiringService.getHiring(hir_id);
            Submission submission = new Submission().setHirId(hiring.getId()).setHirUsername(hiring.getUsername()).setStatus(SubmissionStatus.NONE)
                    .setResId(resume.getId()).setResUsername(resume.getUsername());
            SubmissionDao submissionDao = new SubmissionDao(dsl.configuration());
            if (submissionDao.exists(submission))
                return "already";
            submissionDao.insert(submission);
            return "success";
        }
    }
}
