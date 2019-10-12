package r4g19.offer100.api.cym;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import r4g19.offer100.annotations.cym.APIEntrance;
import r4g19.offer100.api.APIBase;
import r4g19.offer100.jooq.tables.daos.HiringDao;
import r4g19.offer100.jooq.tables.daos.SubmissionDao;
import r4g19.offer100.jooq.tables.pojos.Submission;
import r4g19.offer100.properties.cym.mapping.SubmissionStatus;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("api/user")
@APIEntrance(name = "user")
public class User extends APIBase {
    @RestController
    @RequestMapping("api/user/entrepreneurial")
    public static class Entrepreneurial extends APIBase {
        @GetMapping("hiring/list")
        public HttpEntity<List> hiringList(Authentication authentication) {
            return new ResponseEntity<>(new HiringDao(dsl.configuration()).fetchByUsername(getUsername(authentication)), HttpStatus.OK);
        }

        @GetMapping("submission/list")
        public HttpEntity<List> submissionList(Authentication authentication) {
            return new ResponseEntity<>(new SubmissionDao(dsl.configuration()).fetchByHirUsername(getUsername(authentication)), HttpStatus.OK);
        }

        @PostMapping("submission/{operate}")
        public HttpEntity submissionOperate(@RequestBody Submission[] submissions, @PathVariable SubmissionStatus operate) {
            for (Submission submission : submissions) {
                submission.setStatus(operate);
            }
            new SubmissionDao(dsl.configuration()).update(Arrays.asList(submissions));
            return new ResponseEntity(HttpStatus.OK);
        }
    }
}
