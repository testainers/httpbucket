package com.testainers;

import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.PathSegment;
import jakarta.ws.rs.core.UriInfo;
import org.jboss.resteasy.spi.HttpRequest;

import java.net.URI;
import java.util.List;

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
    public List<PathSegment> pathSegments;
    public MultivaluedMap<String, String> queryParameters;

    public Object body;

    public ResponseBody(HttpRequest request, Object body) {
        this.method = request.getHttpMethod();
        this.remoteAddress = request.getRemoteAddress();
        this.remoteHost = request.getRemoteHost();
        this.headers = request.getHttpHeaders().getRequestHeaders();

        UriInfo uriInfo = request.getUri();
        this.uri = uriInfo.getAbsolutePath();
        this.pathParameters = uriInfo.getPathParameters();
        this.pathSegments = uriInfo.getPathSegments();
        this.queryParameters = uriInfo.getQueryParameters();

        this.body = body;
    }

}
