package com.testainers;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.jboss.resteasy.spi.HttpRequest;

/**
 * @author Eduardo Folly
 */
@Path("/delay")
public class DelayResource {

    @Inject
    HttpRequest request;

    @GET
    @HEAD
    @Path("/{delay}")
    @APIResponses({
            @APIResponse(responseCode = "200"),
            @APIResponse(responseCode = "400"),
            @APIResponse(responseCode = "500"),
    })
    @Produces(MediaType.APPLICATION_JSON)
    public Response withoutBody(
            @Parameter(description = "Delay must be between 0 and 10 seconds.",
                       schema = @Schema(minimum = "1", maximum = "10",
                                        defaultValue = "10"
                       )
            )
            @PathParam("delay") int delay
    ) {
        return getResponse(delay, null);
    }

    @POST
    @PUT
    @PATCH
    @DELETE
    @Path("/{delay}")
    @APIResponses({
            @APIResponse(responseCode = "200"),
            @APIResponse(responseCode = "400"),
            @APIResponse(responseCode = "500"),
    })
    @Produces(MediaType.APPLICATION_JSON)
    public Response withBody(
            @Parameter(description = "Delay must be between 0 and 10 seconds.",
                       schema = @Schema(minimum = "1", maximum = "10",
                                        defaultValue = "10"
                       )
            )
            @PathParam("delay") int delay,
            Object body
    ) {
        return getResponse(delay, body);
    }

    private Response getResponse(int delay, Object body) {
        ResponseBody responseBody = new ResponseBody(request, body);
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
