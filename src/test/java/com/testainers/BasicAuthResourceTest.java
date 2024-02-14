package com.testainers;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.Method;
import jakarta.ws.rs.core.HttpHeaders;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;

/**
 * @author Eduardo Folly
 */
@QuarkusTest
public class BasicAuthResourceTest extends BaseResourceTest {

    private static final String user = "test";
    private static final String pass = "test-pass0";

    @Test
    public void noHeader() {
        for (Method method : METHODS) {
            json(method)
                    .pathParam("user", user)
                    .pathParam("pass", pass)
                    .request(method, "/basic-auth/{user}/{pass}")
                    .then()
                    .statusCode(401)
                    .body("body.body",
                          method == Method.GET ? nullValue() : is(BODY),
                          bodyMatchers(
                                  method,
                                  "body.auth", is(false),
                                  "body.user", is(user),
                                  "body.pass", is(pass),
                                  "body.message",
                                  is("Authorization header not present.")
                          ));
        }
    }

    @Test
    public void emptyHeader() {
        for (Method method : METHODS) {
            json(method)
                    .header(HttpHeaders.AUTHORIZATION, "")
                    .pathParam("user", user)
                    .pathParam("pass", pass)
                    .request(method, "/basic-auth/{user}/{pass}")
                    .then()
                    .statusCode(401)
                    .body("body.body",
                          method == Method.GET ? nullValue() : is(BODY),
                          bodyMatchers(
                                  method,
                                  "body.auth", is(false),
                                  "body.user", is(user),
                                  "body.pass", is(pass),
                                  "body.message",
                                  is("Authorization header not present.")
                          ));
        }
    }

    @Test
    public void success() {
        for (Method method : METHODS) {
            json(method)
                    .auth().preemptive().basic(user, pass)
                    .pathParam("user", user)
                    .pathParam("pass", pass)
                    .request(method, "/basic-auth/{user}/{pass}")
                    .then()
                    .statusCode(200)
                    .body("body.body",
                          method == Method.GET ? nullValue() : is(BODY),
                          bodyMatchers(
                                  method,
                                  "body.auth", is(true),
                                  "body.user", is(user),
                                  "body.pass", is(pass),
                                  "body.message", is("Success.")
                          ));
        }
    }

    @Test
    public void userFail() {
        for (Method method : METHODS) {
            json(method)
                    .auth().preemptive().basic(user, pass)
                    .pathParam("user", user + "Fail")
                    .pathParam("pass", pass)
                    .request(method, "/basic-auth/{user}/{pass}")
                    .then()
                    .statusCode(403)
                    .body("body.body",
                          method == Method.GET ? nullValue() : is(BODY),
                          bodyMatchers(
                                  method,
                                  "body.auth", is(false),
                                  "body.user", is(user + "Fail"),
                                  "body.pass", is(pass),
                                  "body.message", is("Forbidden.")
                          ));
        }
    }

    @Test
    public void passFail() {
        for (Method method : METHODS) {
            json(method)
                    .auth().preemptive().basic(user, pass)
                    .pathParam("user", user)
                    .pathParam("pass", pass + "Fail")
                    .request(method, "/basic-auth/{user}/{pass}")
                    .then()
                    .statusCode(403)
                    .body("body.body",
                          method == Method.GET ? nullValue() : is(BODY),
                          bodyMatchers(
                                  method,
                                  "body.auth", is(false),
                                  "body.user", is(user),
                                  "body.pass", is(pass + "Fail"),
                                  "body.message", is("Forbidden.")
                          ));
        }
    }

}
