package com.testainers

import io.vertx.core.http.HttpServerRequest
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.UriInfo
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses

/**
 * @author Eduardo Folly
 */
@Path("/methods")
@APIResponses(APIResponse(responseCode = "200"))
@Produces(MediaType.APPLICATION_JSON)
class MethodsResource(val request: HttpServerRequest, val uriInfo: UriInfo) {

    @GET
    fun get(): ResponseBody = ResponseBody(request, uriInfo, null)

    @HEAD
    fun head(): ResponseBody = ResponseBody(request, uriInfo, null)

    @POST
    fun post(body: Any?): ResponseBody = ResponseBody(request, uriInfo, body)

    @PUT
    fun put(body: Any?): ResponseBody = ResponseBody(request, uriInfo, body)

    @PATCH
    fun patch(body: Any?): ResponseBody = ResponseBody(request, uriInfo, body)

    @DELETE
    fun delete(body: Any?): ResponseBody = ResponseBody(request, uriInfo, body)
}