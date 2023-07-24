package com.testainers;

import io.quarkus.test.junit.QuarkusTest;

import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;

/**
 * @author Eduardo Folly
 */
@QuarkusTest
public class StatusResourceGetTest extends BaseTest {

    @Test
    public void testStatusGetString() {
        base().get("/status/a")
              .then()
              .statusCode(404)
              .statusLine(containsStringIgnoringCase("Not Found"));
    }

    @Test
    public void testStatusGetDouble() {
        base().get("/status/1.8")
              .then()
              .statusCode(404)
              .statusLine(containsStringIgnoringCase("Not Found"));
    }

    @Test
    public void testStatusGetNegative() {
        base().get("/status/-1")
              .then()
              .statusCode(500)
              .body("body", is("Unknown status code: -1"),
                    bodyMatchers("GET"));
    }

    @Test
    public void testStatusGet0() {
        base().get("/status/0")
              .then()
              .statusCode(500)
              .body("body", is("Unknown status code: 0"),
                    bodyMatchers("GET"));
    }

    @Test
    public void testStatusGet99() {
        base().get("/status/99")
              .then()
              .statusCode(500)
              .body("body", is("Unknown status code: 99"),
                    bodyMatchers("GET"));
    }

    @Test
    public void testStatusGet100() {
        base().get("/status/100")
              .then()
              .statusCode(500)
              .body("body",
                    is("Informational responses are not supported: 100"),
                    bodyMatchers("GET"));
    }

    @Test
    public void testStatusGet199() {
        base().get("/status/199")
              .then()
              .statusCode(500)
              .body("body",
                    is("Informational responses are not supported: 199"),
                    bodyMatchers("GET"));
    }

    @Test
    public void testStatusGet200() {
        base().get("/status/200")
              .then()
              .statusCode(200)
              .body("body", nullValue(),
                    bodyMatchers("GET"));
    }

    @Test
    public void testStatusGet204() {
        base().get("/status/204")
              .then()
              .statusCode(204)
              .body(is(""));
    }

    @Test
    public void testStatusGet205() {
        base().get("/status/205")
              .then()
              .statusCode(205)
              .headers("content-length", "0")
              .body(is(""));
    }

    @Test
    public void testStatusGet304() {
        base().get("/status/304")
              .then()
              .statusCode(304)
              .body(is(""));
    }

    @Test
    public void testStatusGet599() {
        base().get("/status/599")
              .then()
              .statusCode(599)
              .body("body", nullValue(),
                    bodyMatchers("GET"));
    }

    @Test
    public void testStatusGet600() {
        base().get("/status/600")
              .then()
              .statusCode(500)
              .body("body", is("Unknown status code: 600"),
                    bodyMatchers("GET"));
    }

}
