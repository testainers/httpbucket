package com.testainers;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.hamcrest.Matchers.*;

/**
 * @author Eduardo Folly
 */
@QuarkusTest
public class BasicAuthTest extends BaseTest {

    private static final String user = "test";
    private static final String pass = "test-pass0";

    @Test
    public void getNoHeader() {
        base().get("/basic-auth/{user}/{pass}", user, pass)
              .then()
              .statusCode(401)
              .body("body",
                    is(Map.of("auth", false,
                              "user", user,
                              "pass", pass,
                              "message", "Authorization header not present.")),
                    bodyMatchers("GET"));
    }

    @Test
    public void getSuccess() {
        base().auth().preemptive().basic(user, pass)
              .get("/basic-auth/{user}/{pass}", user, pass)
              .then()
              .statusCode(200)
              .body("body", is(Map.of("auth", true,
                                      "user", user,
                                      "pass", pass,
                                      "message", "Success.")),
                    bodyMatchers("GET"));
    }

    @Test
    public void getUserFail() {
        base().auth().preemptive().basic(user, pass)
              .get("/basic-auth/{user}Fail/{pass}", user, pass)
              .then()
              .statusCode(403)
              .body("body", is(Map.of("auth", false,
                                      "user", user + "Fail",
                                      "pass", pass,
                                      "message", "Forbidden.")),
                    bodyMatchers("GET"));
    }

    @Test
    public void getPassFail() {
        base().auth().preemptive().basic(user, pass)
              .get("/basic-auth/{user}/{pass}Fail", user, pass)
              .then()
              .statusCode(403)
              .body("body", is(Map.of("auth", false,
                                      "user", user,
                                      "pass", pass + "Fail",
                                      "message", "Forbidden.")),
                    bodyMatchers("GET"));
    }

    @Test
    public void headNoHeader() {
        base().head("/basic-auth/{user}/{pass}", user, pass)
              .then()
              .statusCode(401)
              .body(is(""));
    }

    @Test
    public void headSuccess() {
        base().auth().preemptive().basic(user, pass)
              .head("/basic-auth/{user}/{pass}", user, pass)
              .then()
              .statusCode(200)
              .body(is(""));
    }

    @Test
    public void headUserFail() {
        base().auth().preemptive().basic(user, pass)
              .head("/basic-auth/{user}Fail/{pass}", user, pass)
              .then()
              .statusCode(403)
              .body(is(""));
    }

    @Test
    public void headPassFail() {
        base().auth().preemptive().basic(user, pass)
              .head("/basic-auth/{user}/{pass}Fail", user, pass)
              .then()
              .statusCode(403)
              .body(is(""));
    }

    @Test
    public void postNoHeader() {
        json().post("/basic-auth/{user}/{pass}", user, pass)
              .then()
              .statusCode(401)
              .body("body",
                    is(Map.of("auth", false,
                              "user", user,
                              "pass", pass,
                              "body", BODY,
                              "message", "Authorization header not present.")),
                    bodyMatchers("POST"));
    }

    @Test
    public void postSuccess() {
        json().auth().preemptive().basic(user, pass)
              .post("/basic-auth/{user}/{pass}", user, pass)
              .then()
              .statusCode(200)
              .body("body", is(Map.of("auth", true,
                                      "user", user,
                                      "pass", pass,
                                      "body", BODY,
                                      "message", "Success.")),
                    bodyMatchers("POST"));
    }

    @Test
    public void postUserFail() {
        json().auth().preemptive().basic(user, pass)
              .post("/basic-auth/{user}Fail/{pass}", user, pass)
              .then()
              .statusCode(403)
              .body("body", is(Map.of("auth", false,
                                      "user", user + "Fail",
                                      "pass", pass,
                                      "body", BODY,
                                      "message", "Forbidden.")),
                    bodyMatchers("POST"));
    }

    @Test
    public void postPassFail() {
        json().auth().preemptive().basic(user, pass)
              .post("/basic-auth/{user}/{pass}Fail", user, pass)
              .then()
              .statusCode(403)
              .body("body", is(Map.of("auth", false,
                                      "user", user,
                                      "pass", pass + "Fail",
                                      "body", BODY,
                                      "message", "Forbidden.")),
                    bodyMatchers("POST"));
    }

    @Test
    public void putNoHeader() {
        json().put("/basic-auth/{user}/{pass}", user, pass)
              .then()
              .statusCode(401)
              .body("body",
                    is(Map.of("auth", false,
                              "user", user,
                              "pass", pass,
                              "body", BODY,
                              "message", "Authorization header not present.")),
                    bodyMatchers("PUT"));
    }

    @Test
    public void putSuccess() {
        json().auth().preemptive().basic(user, pass)
              .put("/basic-auth/{user}/{pass}", user, pass)
              .then()
              .statusCode(200)
              .body("body", is(Map.of("auth", true,
                                      "user", user,
                                      "pass", pass,
                                      "body", BODY,
                                      "message", "Success.")),
                    bodyMatchers("PUT"));
    }

    @Test
    public void putUserFail() {
        json().auth().preemptive().basic(user, pass)
              .put("/basic-auth/{user}Fail/{pass}", user, pass)
              .then()
              .statusCode(403)
              .body("body", is(Map.of("auth", false,
                                      "user", user + "Fail",
                                      "pass", pass,
                                      "body", BODY,
                                      "message", "Forbidden.")),
                    bodyMatchers("PUT"));
    }

    @Test
    public void putPassFail() {
        json().auth().preemptive().basic(user, pass)
              .put("/basic-auth/{user}/{pass}Fail", user, pass)
              .then()
              .statusCode(403)
              .body("body", is(Map.of("auth", false,
                                      "user", user,
                                      "pass", pass + "Fail",
                                      "body", BODY,
                                      "message", "Forbidden.")),
                    bodyMatchers("PUT"));
    }

    @Test
    public void patchNoHeader() {
        json().patch("/basic-auth/{user}/{pass}", user, pass)
              .then()
              .statusCode(401)
              .body("body",
                    is(Map.of("auth", false,
                              "user", user,
                              "pass", pass,
                              "body", BODY,
                              "message", "Authorization header not present.")),
                    bodyMatchers("PATCH"));
    }

    @Test
    public void patchSuccess() {
        json().auth().preemptive().basic(user, pass)
              .patch("/basic-auth/{user}/{pass}", user, pass)
              .then()
              .statusCode(200)
              .body("body", is(Map.of("auth", true,
                                      "user", user,
                                      "pass", pass,
                                      "body", BODY,
                                      "message", "Success.")),
                    bodyMatchers("PATCH"));
    }

    @Test
    public void patchUserFail() {
        json().auth().preemptive().basic(user, pass)
              .patch("/basic-auth/{user}Fail/{pass}", user, pass)
              .then()
              .statusCode(403)
              .body("body", is(Map.of("auth", false,
                                      "user", user + "Fail",
                                      "pass", pass,
                                      "body", BODY,
                                      "message", "Forbidden.")),
                    bodyMatchers("PATCH"));
    }

    @Test
    public void patchPassFail() {
        json().auth().preemptive().basic(user, pass)
              .patch("/basic-auth/{user}/{pass}Fail", user, pass)
              .then()
              .statusCode(403)
              .body("body", is(Map.of("auth", false,
                                      "user", user,
                                      "pass", pass + "Fail",
                                      "body", BODY,
                                      "message", "Forbidden.")),
                    bodyMatchers("PATCH"));
    }

    @Test
    public void deleteNoHeader() {
        json().delete("/basic-auth/{user}/{pass}", user, pass)
              .then()
              .statusCode(401)
              .body("body",
                    is(Map.of("auth", false,
                              "user", user,
                              "pass", pass,
                              "body", BODY,
                              "message", "Authorization header not present.")),
                    bodyMatchers("DELETE"));
    }

    @Test
    public void deleteSuccess() {
        json().auth().preemptive().basic(user, pass)
              .delete("/basic-auth/{user}/{pass}", user, pass)
              .then()
              .statusCode(200)
              .body("body", is(Map.of("auth", true,
                                      "user", user,
                                      "pass", pass,
                                      "body", BODY,
                                      "message", "Success.")),
                    bodyMatchers("DELETE"));
    }

    @Test
    public void deleteUserFail() {
        json().auth().preemptive().basic(user, pass)
              .delete("/basic-auth/{user}Fail/{pass}", user, pass)
              .then()
              .statusCode(403)
              .body("body", is(Map.of("auth", false,
                                      "user", user + "Fail",
                                      "pass", pass,
                                      "body", BODY,
                                      "message", "Forbidden.")),
                    bodyMatchers("DELETE"));
    }

    @Test
    public void deletePassFail() {
        json().auth().preemptive().basic(user, pass)
              .delete("/basic-auth/{user}/{pass}Fail", user, pass)
              .then()
              .statusCode(403)
              .body("body", is(Map.of("auth", false,
                                      "user", user,
                                      "pass", pass + "Fail",
                                      "body", BODY,
                                      "message", "Forbidden.")),
                    bodyMatchers("DELETE"));
    }

}
