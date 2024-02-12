package com.testainers;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;

/**
 * @author Eduardo Folly
 */
@QuarkusTest
public class StatusResourcePatchResourceTest extends BaseResourceTest {

    @Test
    public void testStatusPatchString() {
        base().patch("/status/a")
              .then()
              .statusCode(404)
              .statusLine(containsStringIgnoringCase("Not Found"));
    }

    @Test
    public void testStatusPatchDouble() {
        base().patch("/status/1.8")
              .then()
              .statusCode(404)
              .statusLine(containsStringIgnoringCase("Not Found"));
    }

    @Test
    public void testStatusPatchNegative() {
        json().patch("/status/-1")
              .then()
              .statusCode(500)
              .body("body", is("Unknown status code: -1"),
                    bodyMatchers("PATCH"))
        ;
    }

    @Test
    public void testStatusPatch0() {
        json().patch("/status/0")
              .then()
              .statusCode(500)
              .body("body", is("Unknown status code: 0"),
                    bodyMatchers("PATCH"));
    }

    @Test
    public void testStatusPatch99() {
        json().patch("/status/99")
              .then()
              .statusCode(500)
              .body("body", is("Unknown status code: 99"),
                    bodyMatchers("PATCH"));
    }

    @Test
    public void testStatusPatch100() {
        json().patch("/status/100")
              .then()
              .statusCode(500)
              .body("body",
                    is("Informational responses are not supported: 100"),
                    bodyMatchers("PATCH"));
    }

    @Test
    public void testStatusPatch199() {
        json().patch("/status/199")
              .then()
              .statusCode(500)
              .body("body",
                    is("Informational responses are not supported: 199"),
                    bodyMatchers("PATCH"));
    }

    @Test
    public void testStatusPatch200() {
        json().patch("/status/200")
              .then()
              .statusCode(200)
              .body("body", is(BODY),
                    bodyMatchers("PATCH"));
    }

    @Test
    public void testStatusPatch204() {
        json().patch("/status/204")
              .then()
              .statusCode(204)
              .body(is(""));
    }

    @Test
    public void testStatusPatch205() {
        json().patch("/status/205")
              .then()
              .statusCode(205)
              .headers("content-length", "0")
              .body(is(""));
    }

    @Test
    public void testStatusPatch304() {
        json().patch("/status/304")
              .then()
              .statusCode(304)
              .body(is(""));
    }

    @Test
    public void testStatusPatch599() {
        json().patch("/status/599")
              .then()
              .statusCode(599)
              .body("body", is(BODY),
                    bodyMatchers("PATCH"));
    }

    @Test
    public void testStatusPatch600() {
        json().patch("/status/600")
              .then()
              .statusCode(500)
              .body("body", is("Unknown status code: 600"),
                    bodyMatchers("PATCH"));
    }

}
