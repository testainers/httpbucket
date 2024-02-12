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
@APIResponses({
        @APIResponse(responseCode = "30X", description = "Redirected"),
        @APIResponse(responseCode = "500",
                     description = "'Invalid URL' or " +
                                   "'Invalid URL Scheme' or " +
                                   "'Invalid status code: 30X'")
})
public class RedirectResource {

    @GET
    public Response get(
            @Parameter(description = "URL to redirect.", required = true)
            @QueryParam("url") String url,
            @Parameter(description = "Response status code.",
                       schema = @Schema(minimum = "300", maximum = "399"))
            @QueryParam("code") @DefaultValue("302") Integer code) {

        return internal(url, code);
    }

    @HEAD
    public Response head(
            @Parameter(description = "URL to redirect.", required = true)
            @QueryParam("url") String url,
            @Parameter(description = "Response status code.",
                       schema = @Schema(minimum = "300", maximum = "399"))
            @QueryParam("code") @DefaultValue("302") Integer code) {

        return internal(url, code);
    }

    @POST
    public Response post(
            @Parameter(description = "URL to redirect.", required = true)
            @QueryParam("url") String url,
            @Parameter(description = "Response status code.",
                       schema = @Schema(minimum = "300", maximum = "399"))
            @QueryParam("code") @DefaultValue("302") Integer code) {

        return internal(url, code);
    }

    @PUT
    public Response put(
            @Parameter(description = "URL to redirect.", required = true)
            @QueryParam("url") String url,
            @Parameter(description = "Response status code.",
                       schema = @Schema(minimum = "300", maximum = "399"))
            @QueryParam("code") @DefaultValue("302") Integer code) {

        return internal(url, code);
    }

    @PATCH
    public Response patch(
            @Parameter(description = "URL to redirect.", required = true)
            @QueryParam("url") String url,
            @Parameter(description = "Response status code.",
                       schema = @Schema(minimum = "300", maximum = "399"))
            @QueryParam("code") @DefaultValue("302") Integer code) {

        return internal(url, code);
    }

    @DELETE
    public Response delete(
            @Parameter(description = "URL to redirect.", required = true)
            @QueryParam("url") String url,
            @Parameter(description = "Response status code.",
                       schema = @Schema(minimum = "300", maximum = "399"))
            @QueryParam("code") @DefaultValue("302") Integer code) {

        return internal(url, code);
    }

    private Response internal(String url, Integer code) {
        URI uri;

        try {
            uri = URI.create(url);
        } catch (Exception e) {
            return Response
                    .status(500)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN)
                    .entity(String.format("Invalid URL: %s", url))
                    .build();
        }

        if (uri.getScheme() == null) {
            return Response
                    .status(500)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN)
                    .entity(String.format("Invalid URL Scheme: %s", url))
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
