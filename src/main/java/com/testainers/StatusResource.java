package com.testainers;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
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
    public Response get(
            @Parameter(description = "Code must be between 200 and 599. " +
                                     "Informational responses (1XX) " +
                                     "are not supported.",
                       schema = @Schema(minimum = "200", maximum = "599")
            )
            @PathParam("code") Integer code) {
        return getResponse(code, null);
    }

    @POST
    @PUT
    @PATCH
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response others(
            @Parameter(description = "Code must be between 200 and 599. " +
                                     "Informational responses (1XX) " +
                                     "are not supported.",
                       schema = @Schema(minimum = "200", maximum = "599")
            )
            @PathParam("code") Integer code, Object body) {
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
