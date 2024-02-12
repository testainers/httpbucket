package com.testainers;

import io.vertx.core.http.HttpServerRequest;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;

/**
 * @author Eduardo Folly
 */
@Path("/status/{code}")
@Produces(MediaType.APPLICATION_JSON)
public class StatusResource {

    @Inject
    HttpServerRequest request;

    @Inject
    UriInfo uriInfo;

    @GET
    public Response get(
            @Parameter(description = "Code must be between 200 and 599. " +
                                     "Informational responses (1XX) " +
                                     "are not supported.",
                       schema = @Schema(minimum = "200", maximum = "599")
            ) Integer code) {

        return internal(code, null);
    }

    @HEAD
    public Response head(
            @Parameter(description = "Code must be between 200 and 599. " +
                                     "Informational responses (1XX) " +
                                     "are not supported.",
                       schema = @Schema(minimum = "200", maximum = "599")
            ) Integer code) {

        return internal(code, null);
    }

    @POST
    public Response post(
            @Parameter(description = "Code must be between 200 and 599. " +
                                     "Informational responses (1XX) " +
                                     "are not supported.",
                       schema = @Schema(minimum = "200", maximum = "599")
            ) Integer code, Object body) {

        return internal(code, body);
    }

    @PUT
    public Response put(
            @Parameter(description = "Code must be between 200 and 599. " +
                                     "Informational responses (1XX) " +
                                     "are not supported.",
                       schema = @Schema(minimum = "200", maximum = "599")
            ) Integer code, Object body) {

        return internal(code, body);
    }

    @PATCH
    public Response patch(
            @Parameter(description = "Code must be between 200 and 599. " +
                                     "Informational responses (1XX) " +
                                     "are not supported.",
                       schema = @Schema(minimum = "200", maximum = "599")
            ) Integer code, Object body) {

        return internal(code, body);
    }

    @DELETE
    public Response delete(
            @Parameter(description = "Code must be between 200 and 599. " +
                                     "Informational responses (1XX) " +
                                     "are not supported.",
                       schema = @Schema(minimum = "200", maximum = "599")
            ) Integer code, Object body) {

        return internal(code, body);
    }

    private Response internal(int code, Object body) {
        ResponseBody responseBody = new ResponseBody(request, uriInfo, body);

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
