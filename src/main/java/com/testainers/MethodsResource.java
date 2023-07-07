package com.testainers;

import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.jboss.resteasy.spi.HttpRequest;

import java.net.URI;

/**
 * @author Eduardo Folly
 */
@Path("/methods")
public class MethodsResource {

    @Inject
    HttpRequest request;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public MethodResponseBody get() {
        return new MethodResponseBody(request, null);
    }

    @POST
    @PUT
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public MethodResponseBody others(Object body) {
        return new MethodResponseBody(request, body);
    }


}

/**
 * @author Eduardo Folly
 */
@RegisterForReflection
class MethodResponseBody {

    public URI uri;
    public String method;
    public String remoteAddress;
    public String remoteHost;
    public MultivaluedMap<String, String> headers;
    public MultivaluedMap<String, String> queryParameters;
    public Object body;

    public MethodResponseBody(HttpRequest request, Object body) {
        this.method = request.getHttpMethod();
        this.remoteAddress = request.getRemoteAddress();
        this.remoteHost = request.getRemoteHost();
        this.headers = request.getHttpHeaders().getRequestHeaders();

        UriInfo uriInfo = request.getUri();
        this.uri = uriInfo.getAbsolutePath();
        this.queryParameters = uriInfo.getQueryParameters();

        this.body = body;
    }

}