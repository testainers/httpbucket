package com.testainers;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.jboss.resteasy.spi.HttpRequest;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Eduardo Folly
 */
@Path("/basic-auth")
public class BasicAuthResource {

    @Inject
    HttpRequest request;

    @GET
    @HEAD
    @Path("/{user}/{pass}")
    @APIResponses({
            @APIResponse(responseCode = "200"),
            @APIResponse(responseCode = "401"),
            @APIResponse(responseCode = "500"),
    })
    @Produces(MediaType.APPLICATION_JSON)
    public Response withoutBody(
            @HeaderParam(HttpHeaders.AUTHORIZATION) String auth,
            @PathParam("user") String user,
            @PathParam("pass") String pass
    ) {
        return getResponse(auth, user, pass, null);
    }

    @POST
    @PUT
    @PATCH
    @DELETE
    @Path("/{user}/{pass}")
    @APIResponses({
            @APIResponse(responseCode = "200"),
            @APIResponse(responseCode = "401"),
            @APIResponse(responseCode = "500"),
    })
    @Produces(MediaType.APPLICATION_JSON)
    public Response withBody(
            @HeaderParam(HttpHeaders.AUTHORIZATION) String auth,
            @PathParam("user") String user,
            @PathParam("pass") String pass,
            Object body) {
        return getResponse(auth, user, pass, body);
    }

    private Response getResponse(String auth, String user, String pass,
                                 Object body) {
        ResponseBody responseBody = new ResponseBody(request, body);
        int code = 401;

        Map<String, Object> bodyMap = new HashMap<>();
        bodyMap.put("auth", false);
        bodyMap.put("user", user);
        bodyMap.put("pass", pass);
        bodyMap.put("message", "Unauthorized.");

        if (body != null) {
            bodyMap.put("body", body);
        }

        if (auth == null || auth.isBlank()) {
            code = 500;
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
