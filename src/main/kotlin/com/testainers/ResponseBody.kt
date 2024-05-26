@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package com.testainers

import io.quarkus.runtime.annotations.RegisterForReflection
import io.vertx.core.http.HttpServerRequest
import jakarta.ws.rs.core.*
import java.net.URI

/**
 * @author Eduardo Folly
 */
@RegisterForReflection
class ResponseBody(
    request: HttpServerRequest,
    uriInfo: UriInfo,
    body: Any?,
) {
    var uri: URI
    var method: String = request.method().name()
    var remoteAddress: String = request.remoteAddress().toString()
    var remoteHost: String = request.remoteAddress().host()
    var headers: MultivaluedMap<String, String> = MultivaluedHashMap()
    var pathParameters: MultivaluedMap<String, String>
    var queryParameters: MultivaluedMap<String, String>

    var body: Any?

    init {
        for ((key, value) in request.headers()) {
            headers.add(key, value)
        }

        this.uri = uriInfo.absolutePath
        this.pathParameters = uriInfo.pathParameters
        this.queryParameters = uriInfo.queryParameters

        this.body = body
    }
}
