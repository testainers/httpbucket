package com.testainers;

import io.quarkus.security.Authenticated;
import io.vertx.core.http.HttpServerRequest;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.jboss.resteasy.reactive.RestHeader;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Eduardo Folly
 */
@Authenticated
@Path("/basic-auth/{user}/{pass}")
@APIResponses({
        @APIResponse(responseCode = "200"),
        @APIResponse(responseCode = "401"),
        @APIResponse(responseCode = "403"),
})
@Produces(MediaType.APPLICATION_JSON)
public class BasicAuthResource {

    @Inject
    HttpServerRequest request;

    @Inject
    UriInfo uriInfo;

    @GET
    public Response get(@RestHeader(HttpHeaders.AUTHORIZATION) String auth,
                        String user, String pass) {

        return getResponse(auth, user, pass, null);
    }

    @POST
    public Response post(@RestHeader(HttpHeaders.AUTHORIZATION) String auth,
                         String user, String pass, Object body) {

        return getResponse(auth, user, pass, body);
    }

    @PUT
    public Response put(@RestHeader(HttpHeaders.AUTHORIZATION) String auth,
                        String user, String pass, Object body) {

        return getResponse(auth, user, pass, body);
    }

    @PATCH
    public Response patch(@RestHeader(HttpHeaders.AUTHORIZATION) String auth,
                          String user, String pass, Object body) {

        return getResponse(auth, user, pass, body);
    }

    @DELETE
    public Response delete(@RestHeader(HttpHeaders.AUTHORIZATION) String auth,
                           String user, String pass, Object body) {

        return getResponse(auth, user, pass, body);
    }

    private Response getResponse(String auth, String user, String pass,
                                 Object body) {
        ResponseBody responseBody = new ResponseBody(request, uriInfo, body);
        int code = 403;

        Map<String, Object> bodyMap = new HashMap<>();
        bodyMap.put("auth", false);
        bodyMap.put("user", user);
        bodyMap.put("pass", pass);
        bodyMap.put("message", "Forbidden.");

        if (body != null) {
            bodyMap.put("body", body);
        }

        if (auth == null || auth.isBlank()) {
            code = 401;
            bodyMap.put("message", "Authorization header not present.");
        } else {
            String encoded = Base64
                    .getEncoder()
                    .encodeToString((user + ":" + pass)
                                            .getBytes(StandardCharsets.UTF_8));

            boolean ok = auth.equals("Basic " + encoded);

            bodyMap.put("auth", ok);

            if (ok) {
                code = 200;
                bodyMap.put("message", "Success.");
            }

        }

        responseBody.body = bodyMap;

        return Response.status(code).entity(responseBody).build();
    }

}
