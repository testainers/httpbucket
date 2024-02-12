package com.testainers;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;

/**
 * @author Eduardo Folly
 */
@QuarkusTest
public class StatusResourcePutResourceTest extends BaseResourceTest {

    @Test
    public void testStatusPutString() {
        base().put("/status/a")
              .then()
              .statusCode(404)
              .statusLine(containsStringIgnoringCase("Not Found"));
    }

    @Test
    public void testStatusPutDouble() {
        base().put("/status/1.8")
              .then()
              .statusCode(404)
              .statusLine(containsStringIgnoringCase("Not Found"));
    }

    @Test
    public void testStatusPutNegative() {
        json().put("/status/-1")
              .then()
              .statusCode(500)
              .body("body", is("Unknown status code: -1"),
                    bodyMatchers("PUT"));
    }

    @Test
    public void testStatusPut0() {
        json().put("/status/0")
              .then()
              .statusCode(500)
              .body("body", is("Unknown status code: 0"),
                    bodyMatchers("PUT"));
    }

    @Test
    public void testStatusPut99() {
        json().put("/status/99")
              .then()
              .statusCode(500)
              .body("body", is("Unknown status code: 99"),
                    bodyMatchers("PUT"));
    }

    @Test
    public void testStatusPut100() {
        json().put("/status/100")
              .then()
              .statusCode(500)
              .body("body",
                    is("Informational responses are not supported: 100"),
                    bodyMatchers("PUT"));
    }

    @Test
    public void testStatusPut199() {
        json().put("/status/199")
              .then()
              .statusCode(500)
              .body("body",
                    is("Informational responses are not supported: 199"),
                    bodyMatchers("PUT"));
    }

    @Test
    public void testStatusPut200() {
        json().put("/status/200")
              .then()
              .statusCode(200)
              .body("body", is(BODY),
                    bodyMatchers("PUT"));
    }

    @Test
    public void testStatusPut204() {
        json().put("/status/204")
              .then()
              .statusCode(204)
              .body(is(""));
    }

    @Test
    public void testStatusPut205() {
        json().put("/status/205")
              .then()
              .statusCode(205)
              .headers("content-length", "0")
              .body(is(""));
    }

    @Test
    public void testStatusPut304() {
        json().put("/status/304")
              .then()
              .statusCode(304)
              .body(is(""));
    }

    @Test
    public void testStatusPut599() {
        json().put("/status/599")
              .then()
              .statusCode(599)
              .body("body", is(BODY),
                    bodyMatchers("PUT"));
    }

    @Test
    public void testStatusPut600() {
        json().put("/status/600")
              .then()
              .statusCode(500)
              .body("body", is("Unknown status code: 600"),
                    bodyMatchers("PUT"));
    }

}
