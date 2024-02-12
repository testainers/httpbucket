package com.testainers;

import io.vertx.core.http.HttpServerRequest;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

/**
 * @author Eduardo Folly
 */
@Path("/delay/{delay}")
@APIResponses({
        @APIResponse(responseCode = "200"),
        @APIResponse(responseCode = "400"),
        @APIResponse(responseCode = "500"),
})
@Produces(MediaType.APPLICATION_JSON)
public class DelayResource {

    @Inject
    HttpServerRequest request;

    @Inject
    UriInfo uriInfo;

    @GET
    public Response get(
            @Parameter(description = "Delay must be between 0 and 10 seconds.",
                       schema = @Schema(minimum = "1", maximum = "10",
                                        defaultValue = "10")) int delay) {

        return internal(delay, null);
    }

    @POST
    public Response post(
            @Parameter(description = "Delay must be between 0 and 10 seconds.",
                       schema = @Schema(minimum = "1", maximum = "10",
                                        defaultValue = "10"))
            int delay, Object body) {

        return internal(delay, body);
    }

    @PUT
    public Response put(
            @Parameter(description = "Delay must be between 0 and 10 seconds.",
                       schema = @Schema(minimum = "1", maximum = "10",
                                        defaultValue = "10"))
            int delay, Object body) {

        return internal(delay, body);
    }

    @PATCH
    public Response patch(
            @Parameter(description = "Delay must be between 0 and 10 seconds.",
                       schema = @Schema(minimum = "1", maximum = "10",
                                        defaultValue = "10"))
            int delay, Object body) {

        return internal(delay, body);
    }

    @DELETE
    public Response delete(
            @Parameter(description = "Delay must be between 0 and 10 seconds.",
                       schema = @Schema(minimum = "1", maximum = "10",
                                        defaultValue = "10"))
            int delay, Object body) {

        return internal(delay, body);
    }

    private Response internal(int delay, Object body) {
        ResponseBody responseBody = new ResponseBody(request, uriInfo, body);
        int code = 200;

        if (delay < 0 || delay > 10) {
            code = 400;
            responseBody.body = String.format("Invalid delay: %d", delay);
        } else {
            try {
                Thread.sleep(delay * 1000L);
                responseBody.body =
                        String.format("Slept for %d seconds.", delay);
            } catch (InterruptedException e) {
                code = 500;
                responseBody.body =
                        String.format("Interrupted: %s", e.getMessage());
            }
        }

        return Response.status(code).entity(responseBody).build();
    }

}
