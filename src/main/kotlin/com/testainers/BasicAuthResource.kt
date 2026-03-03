package com.testainers

import io.quarkus.security.Authenticated
import io.vertx.core.http.HttpServerRequest
import jakarta.ws.rs.*
import jakarta.ws.rs.core.HttpHeaders
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import jakarta.ws.rs.core.UriInfo
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses
import org.jboss.resteasy.reactive.RestHeader
import java.nio.charset.StandardCharsets
import java.util.*

/**
 * @author Eduardo Folly
 */
@Authenticated
@Path("/basic-auth/{user}/{pass}")
@APIResponses(
    APIResponse(responseCode = "200"),
    APIResponse(responseCode = "401"),
    APIResponse(responseCode = "403"),
)
@Produces(MediaType.APPLICATION_JSON)
class BasicAuthResource(
    val request: HttpServerRequest,
    val uriInfo: UriInfo,
) {
    @GET
    fun get(
        @RestHeader(HttpHeaders.AUTHORIZATION) auth: String?,
        user: String,
        pass: String,
    ): Response = getResponse(auth, user, pass, null)

    @POST
    fun post(
        @RestHeader(HttpHeaders.AUTHORIZATION) auth: String?,
        user: String,
        pass: String,
        body: Any?,
    ): Response = getResponse(auth, user, pass, body)

    @PUT
    fun put(
        @RestHeader(HttpHeaders.AUTHORIZATION) auth: String?,
        user: String,
        pass: String,
        body: Any?,
    ): Response = getResponse(auth, user, pass, body)

    @PATCH
    fun patch(
        @RestHeader(HttpHeaders.AUTHORIZATION) auth: String?,
        user: String,
        pass: String,
        body: Any?,
    ): Response = getResponse(auth, user, pass, body)

    @DELETE
    fun delete(
        @RestHeader(HttpHeaders.AUTHORIZATION) auth: String?,
        user: String,
        pass: String,
        body: Any?,
    ): Response = getResponse(auth, user, pass, body)

    @HEAD
    fun head(
        @RestHeader(HttpHeaders.AUTHORIZATION) auth: String?,
        user: String,
        pass: String,
    ): Response = getResponse(auth, user, pass, null)

    private fun getResponse(
        auth: String?,
        user: String,
        pass: String,
        body: Any?,
    ): Response {
        val responseBody = ResponseBody(request, uriInfo, body)
        var code = 403

        val bodyMap: MutableMap<String, Any?> = HashMap()
        bodyMap["auth"] = false
        bodyMap["user"] = user
        bodyMap["pass"] = pass
        bodyMap["message"] = "Forbidden."
        bodyMap["body"] = body

        if (auth == null) {
            code = 401
            bodyMap["message"] = "Authorization header not present."
        } else {
            val encoded =
                Base64
                    .getEncoder()
                    .encodeToString(
                        "$user:$pass".toByteArray(StandardCharsets.UTF_8),
                    )

            val ok = auth == "Basic $encoded"

            bodyMap["auth"] = ok

            if (ok) {
                code = 200
                bodyMap["message"] = "Success."
            }
        }

        responseBody.body = bodyMap

        return Response.status(code).entity(responseBody).build()
    }
}
