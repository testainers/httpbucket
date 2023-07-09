package com.testainers;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.jboss.resteasy.spi.HttpRequest;

/**
 * @author Eduardo Folly
 */
@Path("/methods")
public class MethodsResource {

    @Inject
    HttpRequest request;

    @GET
    @APIResponses({@APIResponse(responseCode = "200")})
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseBody get() {
        return new ResponseBody(request, null);
    }

    @POST
    @PUT
    @PATCH
    @DELETE
    @APIResponses({@APIResponse(responseCode = "200")})
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseBody others(Object body) {
        return new ResponseBody(request, body);
    }

}

