package r4g19.offer100.api.cym;

import r4g19.offer100.annotations.cym.API;
import r4g19.offer100.api.APIBase;
import r4g19.offer100.properties.cym.Vars;
import r4g19.offer100.properties.cym.mapping.UserType;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@API("/")
public class Catalog extends APIBase {

    @GET
    public Response index(@Context HttpServletRequest request, @Context UriInfo uriInfo) {
        if (getUserType(request).equals(UserType.Localhost))
            return Response.ok(Vars.API).build();
        return super.index(request, uriInfo);
    }
}