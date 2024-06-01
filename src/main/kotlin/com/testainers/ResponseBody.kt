package com.testainers

import io.vertx.core.http.HttpServerRequest
import jakarta.ws.rs.core.*
import java.net.URI

/**
 * @author Eduardo Folly
 */
data class ResponseBody(
    private val request: HttpServerRequest,
    private val uriInfo: UriInfo,
    var body: Any?,
) {
    val uri: URI get() = uriInfo.absolutePath
    val method: String get() = request.method().name()
    val remoteAddress: String get() = request.remoteAddress().toString()
    val remoteHost: String get() = request.remoteAddress().host()
    val pathParameters: MultivaluedMap<String, String>
        get() = uriInfo.pathParameters
    val queryParameters: MultivaluedMap<String, String>
        get() = uriInfo.queryParameters
    val headers: MultivaluedMap<String, String>
        get() =
            request
                .headers()
                .fold(MultivaluedHashMap()) { acc, (key, value) ->
                    acc.apply { add(key, value) }
                }
}
