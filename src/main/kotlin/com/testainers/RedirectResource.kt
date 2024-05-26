package com.testainers

import jakarta.ws.rs.*
import jakarta.ws.rs.core.*
import org.eclipse.microprofile.openapi.annotations.media.Schema
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses
import java.net.URI

/**
 * @author Eduardo Folly
 */
@Path("/redirect")
@APIResponses(
    APIResponse(responseCode = "30X", description = "Redirected"),
    APIResponse(
        responseCode = "500",
        description =
            "'Invalid URL' or 'Invalid URL Scheme' " +
                "or 'Invalid status code: 30X'",
    ),
)
class RedirectResource {
    @GET
    fun get(
        @Parameter(
            description = "URL to redirect.",
            required = true,
        ) @QueryParam("url") @DefaultValue("") url: String,
        @Parameter(
            description = "Response status code.",
            schema = Schema(minimum = "300", maximum = "399"),
        ) @QueryParam("code") @DefaultValue("302") code: Int,
    ): Response = internal(url, code)

    @POST
    fun post(
        @Parameter(
            description = "URL to redirect.",
            required = true,
        ) @QueryParam("url") @DefaultValue("") url: String,
        @Parameter(
            description = "Response status code.",
            schema = Schema(minimum = "300", maximum = "399"),
        ) @QueryParam("code") @DefaultValue("302") code: Int,
    ): Response = internal(url, code)

    @PUT
    fun put(
        @Parameter(
            description = "URL to redirect.",
            required = true,
        ) @QueryParam("url") @DefaultValue("") url: String,
        @Parameter(
            description = "Response status code.",
            schema = Schema(minimum = "300", maximum = "399"),
        ) @QueryParam("code") @DefaultValue("302") code: Int,
    ): Response = internal(url, code)

    @PATCH
    fun patch(
        @Parameter(
            description = "URL to redirect.",
            required = true,
        ) @QueryParam("url") @DefaultValue("") url: String,
        @Parameter(
            description = "Response status code.",
            schema = Schema(minimum = "300", maximum = "399"),
        ) @QueryParam("code") @DefaultValue("302") code: Int,
    ): Response = internal(url, code)

    @DELETE
    fun delete(
        @Parameter(
            description = "URL to redirect.",
            required = true,
        ) @QueryParam("url") @DefaultValue("") url: String,
        @Parameter(
            description = "Response status code.",
            schema = Schema(minimum = "300", maximum = "399"),
        ) @QueryParam("code") @DefaultValue("302") code: Int,
    ): Response = internal(url, code)

    private fun internal(
        url: String,
        code: Int,
    ): Response {
        val uri: URI

        try {
            uri = URI.create(url)
        } catch (e: Exception) {
            return Response
                .status(500)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN)
                .entity(String.format("Invalid URL: %s", url))
                .build()
        }

        if (uri.scheme == null) {
            return Response
                .status(500)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN)
                .entity(String.format("Invalid URL Scheme: %s", url))
                .build()
        }

        if (code < 300 || code > 399) {
            return Response
                .status(500)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN)
                .entity(String.format("Invalid status code: %d", code))
                .build()
        }

        return Response
            .status(code)
            .header(HttpHeaders.LOCATION, uri.toASCIIString())
            .build()
    }
}
