package com.testainers;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

import java.net.URI;

/**
 * @author Eduardo Folly
 */
@Path("/redirect")
public class RedirectResource {

    @GET
    @HEAD
    @POST
    @PUT
    @PATCH
    @DELETE
    @APIResponses({
            @APIResponse(responseCode = "30X", description = "Redirected"),
            @APIResponse(responseCode = "500",
                         description = "'Invalid URL' or " +
                                       "'Invalid URL Scheme' or " +
                                       "'Invalid status code: 30X'")
    })
    public Response redirect(
            @Parameter(description = "URL to redirect.", required = true)
            @QueryParam("url") String url,
            @Parameter(description = "Response status code.",
                       schema = @Schema(minimum = "300", maximum = "399"))
            @QueryParam("code")
            @DefaultValue("302") Integer code) {

        if (url == null) {
            return Response
                    .status(500)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN)
                    .entity("Invalid URL")
                    .build();
        }

        URI uri = URI.create(url);

        if (uri.getScheme() == null) {
            return Response
                    .status(500)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN)
                    .entity("Invalid URL Scheme")
                    .build();
        }

        if (code < 300 || code > 399) {
            return Response
                    .status(500)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN)
                    .entity(String.format("Invalid status code: %d", code))
                    .build();
        }

        return Response.status(code)
                       .header(HttpHeaders.LOCATION, uri.toASCIIString())
                       .build();
    }

}
