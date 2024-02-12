package com.testainers;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.jboss.resteasy.reactive.RestHeader;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author Eduardo Folly
 */
@Path("/length/{size}")
@APIResponses({
        @APIResponse(responseCode = "200", content = {
                @Content(mediaType = MediaType.TEXT_PLAIN),
                @Content(mediaType = MediaType.APPLICATION_OCTET_STREAM)
        }),
        @APIResponse(responseCode = "500", description = "Invalid size: X.")
})
public class LengthResource {

    @GET
    public Response get(@RestHeader(HttpHeaders.ACCEPT) String accept,
                        @Parameter(description = "Size must be " +
                                                 "between 1 and 2048.",
                                   schema = @Schema(minimum = "1",
                                                    maximum = "2048",
                                                    defaultValue = "10"
                                   )) int size) {

        return internal(accept, size);
    }

    @HEAD
    public Response head(@RestHeader(HttpHeaders.ACCEPT) String accept,
                         @Parameter(description = "Size must be " +
                                                  "between 1 and 2048.",
                                    schema = @Schema(minimum = "1",
                                                     maximum = "2048",
                                                     defaultValue = "10"
                                    )) int size) {

        return internal(accept, size);
    }

    @POST
    public Response post(@RestHeader(HttpHeaders.ACCEPT) String accept,
                         @Parameter(description = "Size must be " +
                                                  "between 1 and 2048.",
                                    schema = @Schema(minimum = "1",
                                                     maximum = "2048",
                                                     defaultValue = "10"
                                    )) int size) {

        return internal(accept, size);
    }

    @PUT
    public Response put(@RestHeader(HttpHeaders.ACCEPT) String accept,
                        @Parameter(description = "Size must be " +
                                                 "between 1 and 2048.",
                                   schema = @Schema(minimum = "1",
                                                    maximum = "2048",
                                                    defaultValue = "10"
                                   )) int size) {

        return internal(accept, size);
    }

    @PATCH
    public Response patch(@RestHeader(HttpHeaders.ACCEPT) String accept,
                          @Parameter(description = "Size must be " +
                                                   "between 1 and 2048.",
                                     schema = @Schema(minimum = "1",
                                                      maximum = "2048",
                                                      defaultValue = "10"
                                     )) int size) {

        return internal(accept, size);
    }

    @DELETE
    public Response delete(@RestHeader(HttpHeaders.ACCEPT) String accept,
                           @Parameter(description = "Size must be " +
                                                    "between 1 and 2048.",
                                      schema = @Schema(minimum = "1",
                                                       maximum = "2048",
                                                       defaultValue = "10"
                                      )) int size) {

        return internal(accept, size);
    }

    private Response internal(String accept, int size) {
        if (size < 1 || size > 2048) {
            return Response
                    .status(500)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN)
                    .entity(String.format("Invalid size: %d", size))
                    .build();
        }

        if (MediaType.APPLICATION_OCTET_STREAM.equals(accept)) {
            return Response
                    .ok(new byte[size], MediaType.APPLICATION_OCTET_STREAM_TYPE)
                    .build();
        } else {
            return Response
                    .ok(IntStream.range(0, size)
                                 .boxed()
                                 .map(integer -> "0")
                                 .collect(Collectors.joining()),
                        MediaType.TEXT_PLAIN_TYPE)
                    .build();
        }
    }

}
