package com.testainers

import io.vertx.core.http.HttpServerRequest
import jakarta.ws.rs.*
import jakarta.ws.rs.core.*
import org.eclipse.microprofile.openapi.annotations.media.Schema
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter

/**
 * @author Eduardo Folly
 */
@Path("/status/{code}")
@Produces(MediaType.APPLICATION_JSON)
class StatusResource(val request: HttpServerRequest, val uriInfo: UriInfo) {
    @GET
    fun get(
        @Parameter(
            description = "Code must be between 200 and 599. " +
                    "Informational responses (1XX) are not supported.",
            schema = Schema(minimum = "200", maximum = "599")
        ) code: Int
    ): Response {
        return internal(code, null)
    }

    @POST
    fun post(
        @Parameter(
            description = "Code must be between 200 and 599. " +
                    "Informational responses (1XX) are not supported.",
            schema = Schema(minimum = "200", maximum = "599")
        ) code: Int, body: Any?
    ): Response {
        return internal(code, body)
    }

    @PUT
    fun put(
        @Parameter(
            description = "Code must be between 200 and 599. " +
                    "Informational responses (1XX) are not supported.",
            schema = Schema(minimum = "200", maximum = "599")
        ) code: Int, body: Any?
    ): Response {
        return internal(code, body)
    }

    @PATCH
    fun patch(
        @Parameter(
            description = "Code must be between 200 and 599. " +
                    "Informational responses (1XX) are not supported.",
            schema = Schema(minimum = "200", maximum = "599")
        ) code: Int, body: Any?
    ): Response = internal(code, body)

    @DELETE
    fun delete(
        @Parameter(
            description = "Code must be between 200 and 599. " +
                    "Informational responses (1XX) are not supported.",
            schema = Schema(minimum = "200", maximum = "599")
        ) code: Int, body: Any?
    ): Response = internal(code, body)

    private fun internal(code: Int, body: Any?): Response {
        var status = code
        val responseBody = ResponseBody(request, uriInfo, body)

        if (status < 200 || status > 599) {
            val message = if (status in 100..199) {
                "Informational responses are not supported: %d"
            } else {
                "Unknown status code: %d"
            }

            responseBody.body = String.format(message, status)
            status = 500
        }

        return Response.status(status).entity(responseBody).build()
    }
}