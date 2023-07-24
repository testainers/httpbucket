package com.testainers;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;

/**
 * @author Eduardo Folly
 */
@QuarkusTest
public class StatusResourcePostResourceTest extends BaseResourceTest {

    @Test
    public void testStatusPostString() {
        base().post("/status/a")
              .then()
              .statusCode(404)
              .statusLine(containsStringIgnoringCase("Not Found"));
    }

    @Test
    public void testStatusPostDouble() {
        base().post("/status/1.8")
              .then()
              .statusCode(404)
              .statusLine(containsStringIgnoringCase("Not Found"));
    }

    @Test
    public void testStatusPostNegative() {
        json().post("/status/-1")
              .then()
              .statusCode(500)
              .body("body", is("Unknown status code: -1"),
                    bodyMatchers("POST"));
    }

    @Test
    public void testStatusPost0() {
        json().post("/status/0")
              .then()
              .statusCode(500)
              .body("body", is("Unknown status code: 0"),
                    bodyMatchers("POST"));
    }

    @Test
    public void testStatusPost99() {
        json().post("/status/99")
              .then()
              .statusCode(500)
              .body("body", is("Unknown status code: 99"),
                    bodyMatchers("POST"));
    }

    @Test
    public void testStatusPost100() {
        json().post("/status/100")
              .then()
              .statusCode(500)
              .body("body",
                    is("Informational responses are not supported: 100"),
                    bodyMatchers("POST"));
    }

    @Test
    public void testStatusPost199() {
        json().post("/status/199")
              .then()
              .statusCode(500)
              .body("body",
                    is("Informational responses are not supported: 199"),
                    bodyMatchers("POST"));
    }

    @Test
    public void testStatusPost200() {
        json().post("/status/200")
              .then()
              .statusCode(200)
              .body("body", is(BODY),
                    bodyMatchers("POST"));
    }

    @Test
    public void testStatusPost204() {
        json().post("/status/204")
              .then()
              .statusCode(204)
              .body(is(""));
    }

    @Test
    public void testStatusPost205() {
        json().post("/status/205")
              .then()
              .statusCode(205)
              .headers("content-length", "0")
              .body(is(""));
    }

    @Test
    public void testStatusPost304() {
        json().post("/status/304")
              .then()
              .statusCode(304)
              .body(is(""));
    }

    @Test
    public void testStatusPost599() {
        json().post("/status/599")
              .then()
              .statusCode(599)
              .body("body", is(BODY),
                    bodyMatchers("POST"));
    }

    @Test
    public void testStatusPost600() {
        json().post("/status/600")
              .then()
              .statusCode(500)
              .body("body", is("Unknown status code: 600"),
                    bodyMatchers("POST"));
    }

}
