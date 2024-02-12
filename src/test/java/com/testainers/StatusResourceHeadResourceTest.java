package com.testainers;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.*;

/**
 * @author Eduardo Folly
 */
@QuarkusTest
public class StatusResourceHeadResourceTest extends BaseResourceTest {

    @Test
    public void testStatusHeadString() {
        base().head("/status/a")
              .then()
              .statusCode(404)
              .statusLine(containsStringIgnoringCase("Not Found"));
    }

    @Test
    public void testStatusHeadDouble() {
        base().head("/status/1.8")
              .then()
              .statusCode(404)
              .statusLine(containsStringIgnoringCase("Not Found"));
    }

    @Test
    public void testStatusHeadNegative() {
        base().head("/status/-1")
              .then()
              .statusCode(500)
              .body(is(""));
    }

    @Test
    public void testStatusHead0() {
        base().head("/status/0")
              .then()
              .statusCode(500)
              .body(is(""));
    }

    @Test
    public void testStatusHead99() {
        base().head("/status/99")
              .then()
              .statusCode(500)
              .body(is(""));
    }

    @Test
    public void testStatusHead100() {
        base().head("/status/100")
              .then()
              .statusCode(500)
              .body(is(""));
    }

    @Test
    public void testStatusHead199() {
        base().head("/status/199")
              .then()
              .statusCode(500)
              .body(is(""));
    }

    @Test
    public void testStatusHead200() {
        base().head("/status/200")
              .then()
              .statusCode(200)
              .body(is(""));
    }

    @Test
    public void testStatusHead204() {
        base().head("/status/204")
              .then()
              .statusCode(204)
              .body(is(""));
    }

    @Test
    public void testStatusHead205() {
        base().head("/status/205")
              .then()
              .statusCode(205)
              .headers("content-length", "0")
              .body(is(""));
    }

    @Test
    public void testStatusHead304() {
        base().head("/status/304")
              .then()
              .statusCode(304)
              .body(is(""));
    }

    @Test
    public void testStatusHead599() {
        base().head("/status/599")
              .then()
              .statusCode(599)
              .body(is(""));
    }

    @Test
    public void testStatusHead600() {
        base().head("/status/600")
              .then()
              .statusCode(500)
              .body(is(""));
    }

}
