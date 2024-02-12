package com.testainers;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.Method;
import org.junit.jupiter.api.Test;

import java.util.Map;

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
            base().pathParam("user", user)
                  .pathParam("pass", pass)
                  .request(method, "/basic-auth/{user}/{pass}")
                  .then()
                  .statusCode(401)
                  .body("body",
                        is(Map.of("auth", false,
                                  "user", user,
                                  "pass", pass,
                                  "message",
                                  "Authorization header not present.")),
                        bodyMatchers(method));
        }
    }

    @Test
    public void success() {
        for (Method method : METHODS) {
            base().auth().preemptive().basic(user, pass)
                  .pathParam("user", user)
                  .pathParam("pass", pass)
                  .request(method, "/basic-auth/{user}/{pass}")
                  .then()
                  .statusCode(200)
                  .body("body",
                        is(Map.of("auth", true,
                                  "user", user,
                                  "pass", pass,
                                  "message", "Success.")),
                        bodyMatchers(method));
        }
    }

    @Test
    public void userFail() {
        for (Method method : METHODS) {
            base().auth().preemptive().basic(user, pass)
                  .pathParam("user", user + "Fail")
                  .pathParam("pass", pass)
                  .request(method, "/basic-auth/{user}/{pass}")
                  .then()
                  .statusCode(403)
                  .body("body",
                        is(Map.of("auth", false,
                                  "user", user + "Fail",
                                  "pass", pass,
                                  "message", "Forbidden.")),
                        bodyMatchers(method));
        }
    }

    @Test
    public void passFail() {
        for (Method method : METHODS) {
            base().auth().preemptive().basic(user, pass)
                  .pathParam("user", user)
                  .pathParam("pass", pass + "Fail")
                  .request(method, "/basic-auth/{user}/{pass}")
                  .then()
                  .statusCode(403)
                  .body("body",
                        is(Map.of("auth", false,
                                  "user", user,
                                  "pass", pass + "Fail",
                                  "message", "Forbidden.")),
                        bodyMatchers(method));
        }
    }

}
