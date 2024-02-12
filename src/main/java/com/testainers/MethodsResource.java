package com.testainers;

import io.vertx.core.http.HttpServerRequest;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

/**
 * @author Eduardo Folly
 */
@Path("/methods")
@APIResponses({@APIResponse(responseCode = "200")})
@Produces(MediaType.APPLICATION_JSON)
public class MethodsResource {

    @Inject
    HttpServerRequest request;

    @Inject
    UriInfo uriInfo;

    @GET
    public ResponseBody get() {
        return new ResponseBody(request, uriInfo, null);
    }

    @HEAD
    public ResponseBody head() {
        return new ResponseBody(request, uriInfo, null);
    }

    @POST
    public ResponseBody post(Object body) {
        return new ResponseBody(request, uriInfo, body);
    }

    @PUT
    public ResponseBody put(Object body) {
        return new ResponseBody(request, uriInfo, body);
    }

    @PATCH
    public ResponseBody patch(Object body) {
        return new ResponseBody(request, uriInfo, body);
    }

    @DELETE
    public ResponseBody delete(Object body) {
        return new ResponseBody(request, uriInfo, body);
    }

}

