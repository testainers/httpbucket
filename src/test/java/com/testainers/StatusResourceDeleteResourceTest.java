package com.testainers;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;

/**
 * @author Eduardo Folly
 */
@QuarkusTest
public class StatusResourceDeleteResourceTest extends BaseResourceTest {

    @Test
    public void testStatusDeleteString() {
        json().delete("/status/a")
              .then()
              .statusCode(404)
              .statusLine(containsStringIgnoringCase("Not Found"));
    }

    @Test
    public void testStatusDeleteDouble() {
        json().delete("/status/1.8")
              .then()
              .statusCode(404)
              .statusLine(containsStringIgnoringCase("Not Found"));
    }

    @Test
    public void testStatusDeleteNegative() {
        json().delete("/status/-1")
              .then()
              .statusCode(500)
              .body("body", is("Unknown status code: -1"),
                    bodyMatchers("DELETE"));
    }

    @Test
    public void testStatusDelete0() {
        json().delete("/status/0")
              .then()
              .statusCode(500)
              .body("body", is("Unknown status code: 0"),
                    bodyMatchers("DELETE"));
    }

    @Test
    public void testStatusDelete99() {
        json().delete("/status/99")
              .then()
              .statusCode(500)
              .body("body", is("Unknown status code: 99"),
                    bodyMatchers("DELETE"));
    }

    @Test
    public void testStatusDelete100() {
        json().delete("/status/100")
              .then()
              .statusCode(500)
              .body("body",
                    is("Informational responses are not supported: 100"),
                    bodyMatchers("DELETE"));
    }

    @Test
    public void testStatusDelete199() {
        json().delete("/status/199")
              .then()
              .statusCode(500)
              .body("body",
                    is("Informational responses are not supported: 199"),
                    bodyMatchers("DELETE"));
    }

    @Test
    public void testStatusDelete200() {
        json().delete("/status/200")
              .then()
              .statusCode(200)
              .body("body", is(BODY),
                    bodyMatchers("DELETE"));
    }

    @Test
    public void testStatusDelete204() {
        json().delete("/status/204")
              .then()
              .statusCode(204)
              .body(is(""));
    }

    @Test
    public void testStatusDelete205() {
        json().delete("/status/205")
              .then()
              .statusCode(205)
              .headers("content-length", "0")
              .body(is(""));
    }

    @Test
    public void testStatusDelete304() {
        json().delete("/status/304")
              .then()
              .statusCode(304)
              .body(is(""));
    }

    @Test
    public void testStatusDelete599() {
        json().delete("/status/599")
              .then()
              .statusCode(599)
              .body("body", is(BODY),
                    bodyMatchers("DELETE")
              );
    }

    @Test
    public void testStatusDelete600() {
        json().delete("/status/600")
              .then()
              .statusCode(500)
              .body("body", is("Unknown status code: 600"),
                    bodyMatchers("DELETE"));
    }

}
