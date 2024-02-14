package com.testainers;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.Method;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;

/**
 * @author Eduardo Folly
 */
@QuarkusTest
public class StatusResourceTest extends BaseResourceTest {

    @Test
    public void statusString() {
        for (Method method : METHODS) {
            json(method)
                    .request(method, "/status/a")
                    .then()
                    .statusCode(404)
                    .statusLine(containsStringIgnoringCase("Not Found"));
        }
    }

    @Test
    public void statusDouble() {
        for (Method method : METHODS) {
            json(method)
                    .request(method, "/status/1.8")
                    .then()
                    .statusCode(404)
                    .statusLine(containsStringIgnoringCase("Not Found"));
        }
    }

    @Test
    public void statusNegative() {
        for (Method method : METHODS) {
            json(method).request(method, "/status/-1")
                        .then()
                        .statusCode(500)
                        .body("body", is("Unknown status code: -1"),
                              bodyMatchers(method));
        }
    }

    @Test
    public void status0() {
        for (Method method : METHODS) {
            json(method).request(method, "/status/0")
                        .then()
                        .statusCode(500)
                        .body("body", is("Unknown status code: 0"),
                              bodyMatchers(method));
        }
    }

    @Test
    public void status99() {
        for (Method method : METHODS) {
            json(method).request(method, "/status/99")
                        .then()
                        .statusCode(500)
                        .body("body", is("Unknown status code: 99"),
                              bodyMatchers(method));
        }
    }

    @Test
    public void status100() {
        for (Method method : METHODS) {
            json(method).request(method, "/status/100")
                        .then()
                        .statusCode(500)
                        .body("body",
                              is("Informational responses are not supported: 100"),
                              bodyMatchers(method));
        }
    }

    @Test
    public void status199() {
        for (Method method : METHODS) {
            json(method)
                    .request(method, "/status/199")
                    .then()
                    .statusCode(500)
                    .body("body",
                          is("Informational responses are not supported: 199"),
                          bodyMatchers(method));
        }
    }

    @Test
    public void status200() {
        for (Method method : METHODS) {
            json(method)
                    .request(method, "/status/200")
                    .then()
                    .statusCode(200)
                    .body("body",
                          method == Method.GET ? not(BODY) : is(BODY),
                          bodyMatchers(method));
        }
    }

    @Test
    public void status204() {
        for (Method method : METHODS) {
            json(method).request(method, "/status/204")
                        .then()
                        .statusCode(204)
                        .body(is(""));
        }
    }

    @Test
    public void status205() {
        for (Method method : METHODS) {
            json(method).request(method, "/status/205")
                        .then()
                        .statusCode(205)
                        .headers("content-length", "0")
                        .body(is(""));
        }
    }

    @Test
    public void status304() {
        for (Method method : METHODS) {
            json(method).request(method, "/status/304")
                        .then()
                        .statusCode(304)
                        .body(is(""));
        }
    }

    @Test
    public void status599() {
        for (Method method : METHODS) {
            json(method).request(method, "/status/599")
                        .then()
                        .statusCode(599)
                        .body("body",
                              method == Method.GET ? not(BODY) : is(BODY),
                              bodyMatchers(method));
        }
    }

    @Test
    public void status600() {
        for (Method method : METHODS) {
            json(method).request(method, "/status/600")
                        .then()
                        .statusCode(500)
                        .body("body", is("Unknown status code: 600"),
                              bodyMatchers(method));
        }
    }

}
