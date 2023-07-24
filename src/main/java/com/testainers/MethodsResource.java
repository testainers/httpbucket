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
    @HEAD
    @APIResponses({@APIResponse(responseCode = "200")})
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseBody withoutBody() {
        return new ResponseBody(request, null);
    }

    @POST
    @PUT
    @PATCH
    @DELETE
    @APIResponses({@APIResponse(responseCode = "200")})
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseBody withBody(Object body) {
        return new ResponseBody(request, body);
    }

}

