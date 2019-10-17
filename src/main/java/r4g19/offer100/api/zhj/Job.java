package r4g19.offer100.api.zhj;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import r4g19.offer100.annotations.cym.API;
import r4g19.offer100.api.APIBase;
import r4g19.offer100.jooq.tables.pojos.Collection;
import r4g19.offer100.jooq.tables.pojos.Hiring;
import r4g19.offer100.service.zhj.JobService;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@API("user/job")
public class Job extends APIBase {
    @Autowired
    JobService jobService;

    @GET
    @Path("/search")
    public List<Hiring> searchJobs() {
        return jobService.searchJob(new Hiring());
    }

    @POST
    @Path("/addToCollection")
    public String addToCollection(Hiring hiring, @Context SecurityContext securityContext) {
        String u = APIBase.getUsername(securityContext);
        //u = "1";
        Collection collection = new Collection(u,hiring.getUsername(),hiring.getId(),1,
                new Timestamp(new Date().getTime()), 0);
        jobService.manageCollectionJob(collection, JobService.ADD);
        return "success";
    }

    @POST
    @Path("/delCollection")
    public String delCollection(Collection collection) {
        jobService.manageCollectionJob(collection, JobService.DELETE);
        return "success";
    }

    @GET
    @Path("/collection")
    public List<Collection> getCollection(@Context SecurityContext securityContext) {
        String u = APIBase.getUsername(securityContext);
        //u = "1";
        Collection c = new Collection();
        c.setPerUsername(u);
        return jobService.manageCollectionJob(c, JobService.SEARCH);
    }
}
