package com.testainers;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.spi.HttpRequest;

/**
 * @author Eduardo Folly
 */
@Path("/status/{code}")
public class StatusResource {

    @Inject
    HttpRequest request;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("code") Integer code) {
        return getResponse(code, null);
    }

    @POST
    @PUT
    @PATCH
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response others(@PathParam("code") Integer code, Object body) {
        return getResponse(code, body);
    }

    private Response getResponse(int code, Object body) {
        ResponseBody responseBody = new ResponseBody(request, body);

        if (code < 200 || code > 599) {
            String message;

            if (code > 99 && code < 200) {
                message = "Informational responses are not supported: %d";
            } else {
                message = "Unknown status code: %d";
            }

            responseBody.body = String.format(message, code);
            code = 500;
        }

        return Response.status(code).entity(responseBody).build();
    }

}
