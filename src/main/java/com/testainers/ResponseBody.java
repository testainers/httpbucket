package com.testainers;

import io.quarkus.runtime.annotations.RegisterForReflection;
import io.vertx.core.http.HttpServerRequest;
import jakarta.ws.rs.core.MultivaluedHashMap;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.UriInfo;

import java.net.URI;
import java.util.Map;

/**
 * @author Eduardo Folly
 */
@RegisterForReflection
public class ResponseBody {

    public URI uri;
    public String method;
    public String remoteAddress;
    public String remoteHost;
    public MultivaluedMap<String, String> headers;
    public MultivaluedMap<String, String> pathParameters;
    public MultivaluedMap<String, String> queryParameters;

    public Object body;

    public ResponseBody(HttpServerRequest request, UriInfo uriInfo,
                        Object body) {

        this.method = request.method().name();
        this.remoteAddress = request.remoteAddress().toString();
        this.remoteHost = request.remoteAddress().host();

        headers = new MultivaluedHashMap<>();

        for (Map.Entry<String, String> entry : request.headers()) {
            headers.add(entry.getKey(), entry.getValue());
        }

        this.uri = uriInfo.getAbsolutePath();
        this.pathParameters = uriInfo.getPathParameters();
        this.queryParameters = uriInfo.getQueryParameters();

        this.body = body;
    }

}
