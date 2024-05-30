package com.testainers

import jakarta.ws.rs.*
import jakarta.ws.rs.core.*
import org.eclipse.microprofile.openapi.annotations.media.Content
import org.eclipse.microprofile.openapi.annotations.media.Schema
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses
import org.jboss.resteasy.reactive.RestHeader

/**
 * @author Eduardo Folly
 */
@Path("/length/{size}")
@APIResponses(
    APIResponse(
        responseCode = "200",
        content = [
            Content(
                mediaType = MediaType.TEXT_PLAIN,
            ),
            Content(mediaType = MediaType.APPLICATION_OCTET_STREAM),
        ],
    ),
    APIResponse(responseCode = "500", description = "Invalid size: X."),
)
class LengthResource {
    @GET
    fun get(
        @RestHeader(HttpHeaders.ACCEPT) accept: String,
        @Parameter(
            description = "Size must be between 1 and 2048.",
            schema =
                Schema(
                    minimum = "1",
                    maximum = "2048",
                    defaultValue = "10",
                ),
        ) size: Int,
    ): Response = internal(accept, size)

    @POST
    fun post(
        @RestHeader(HttpHeaders.ACCEPT) accept: String,
        @Parameter(
            description = "Size must be between 1 and 2048.",
            schema =
                Schema(
                    minimum = "1",
                    maximum = "2048",
                    defaultValue = "10",
                ),
        ) size: Int,
    ): Response = internal(accept, size)

    @PUT
    fun put(
        @RestHeader(HttpHeaders.ACCEPT) accept: String,
        @Parameter(
            description = "Size must be between 1 and 2048.",
            schema =
                Schema(
                    minimum = "1",
                    maximum = "2048",
                    defaultValue = "10",
                ),
        ) size: Int,
    ): Response = internal(accept, size)

    @PATCH
    fun patch(
        @RestHeader(HttpHeaders.ACCEPT) accept: String,
        @Parameter(
            description = "Size must be between 1 and 2048.",
            schema =
                Schema(
                    minimum = "1",
                    maximum = "2048",
                    defaultValue = "10",
                ),
        ) size: Int,
    ): Response = internal(accept, size)

    @DELETE
    fun delete(
        @RestHeader(HttpHeaders.ACCEPT) accept: String,
        @Parameter(
            description = "Size must be between 1 and 2048.",
            schema =
                Schema(
                    minimum = "1",
                    maximum = "2048",
                    defaultValue = "10",
                ),
        ) size: Int,
    ): Response = internal(accept, size)

    private fun internal(
        accept: String,
        size: Int,
    ): Response =
        if (size < 1 || size > 2048) {
            Response
                .status(500)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN)
                .entity(String.format("Invalid size: %d", size))
                .build()
        } else {
            if (MediaType.APPLICATION_OCTET_STREAM == accept) {
                Response
                    .ok(
                        ByteArray(size),
                        MediaType.APPLICATION_OCTET_STREAM_TYPE,
                    ).build()
            } else {
                Response.ok("0".repeat(size), MediaType.TEXT_PLAIN_TYPE).build()
            }
        }
}
