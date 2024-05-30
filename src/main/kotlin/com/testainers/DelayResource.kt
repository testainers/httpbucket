package com.testainers

import io.vertx.core.http.HttpServerRequest
import jakarta.ws.rs.*
import jakarta.ws.rs.core.*
import org.eclipse.microprofile.openapi.annotations.media.Schema
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses

/**
 * @author Eduardo Folly
 */
@Path("/delay/{delay}")
@APIResponses(
    APIResponse(responseCode = "200"),
    APIResponse(responseCode = "400"),
    APIResponse(responseCode = "500"),
)
@Produces(MediaType.APPLICATION_JSON)
class DelayResource(
    val request: HttpServerRequest,
    val uriInfo: UriInfo,
) {
    @GET
    fun get(
        @Parameter(
            description = "Delay must be between 0 and 10 seconds.",
            schema = Schema(minimum = "0", maximum = "10", defaultValue = "10"),
        ) delay: Int,
    ): Response = internal(delay, null)

    @POST
    fun post(
        @Parameter(
            description = "Delay must be between 0 and 10 seconds.",
            schema = Schema(minimum = "0", maximum = "10", defaultValue = "10"),
        ) delay: Int,
        body: Any?,
    ): Response = internal(delay, body)

    @PUT
    fun put(
        @Parameter(
            description = "Delay must be between 0 and 10 seconds.",
            schema = Schema(minimum = "0", maximum = "10", defaultValue = "10"),
        ) delay: Int,
        body: Any?,
    ): Response = internal(delay, body)

    @PATCH
    fun patch(
        @Parameter(
            description = "Delay must be between 0 and 10 seconds.",
            schema = Schema(minimum = "0", maximum = "10", defaultValue = "10"),
        ) delay: Int,
        body: Any?,
    ): Response = internal(delay, body)

    @DELETE
    fun delete(
        @Parameter(
            description = "Delay must be between 0 and 10 seconds.",
            schema = Schema(minimum = "0", maximum = "10", defaultValue = "10"),
        ) delay: Int,
        body: Any?,
    ): Response = internal(delay, body)

    private fun internal(
        delay: Int,
        body: Any?,
    ): Response {
        val responseBody = ResponseBody(request, uriInfo, body)
        var code = 200

        if (delay < 0 || delay > 10) {
            code = 400
            responseBody.body = String.format("Invalid delay: %d", delay)
        } else {
            Thread.sleep(delay * 1000L)
            responseBody.body =
                String.format("Slept for %d seconds.", delay)
        }

        return Response.status(code).entity(responseBody).build()
    }
}
