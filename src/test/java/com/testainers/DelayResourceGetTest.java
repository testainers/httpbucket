package com.testainers;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;

/**
 * @author Eduardo Folly
 */
@QuarkusTest
public class DelayResourceGetTest extends BaseResourceTest {

    @Test
    public void testDelayGetEmpty() {
        base().get("/delay")
              .then()
              .statusCode(404)
              .statusLine(containsStringIgnoringCase("Not Found"));
    }

    @Test
    public void testDelayGetString() {
        base().get("/delay/a")
              .then()
              .statusCode(404)
              .statusLine(containsStringIgnoringCase("Not Found"));
    }

    @Test
    public void testDelayGetDouble() {
        base().get("/delay/1.8")
              .then()
              .statusCode(404)
              .statusLine(containsStringIgnoringCase("Not Found"));
    }

    @Test
    public void testDelayGetNegative() {
        base().get("/delay/-1")
              .then()
              .statusCode(400)
              .body("body", is("Invalid delay: -1"),
                    bodyMatchers("GET"));
    }

    @Test
    public void testDelayGet0() {
        base().get("/delay/0")
              .then()
              .statusCode(200)
              .body("body", is("Slept for 0 seconds."),
                    bodyMatchers("GET"));
    }

    @Test
    public void testDelayGet1() {
        base().get("/delay/1")
              .then()
              .statusCode(200)
              .body("body", is("Slept for 1 seconds."),
                    bodyMatchers("GET"));
    }

    @Test
    public void testDelayGet10() {
        base().get("/delay/10")
              .then()
              .statusCode(200)
              .body("body", is("Slept for 10 seconds."),
                    bodyMatchers("GET"));
    }

    @Test
    public void testDelayGet11() {
        base().get("/delay/11")
              .then()
              .statusCode(400)
              .body("body", is("Invalid delay: 11"),
                    bodyMatchers("GET"));
    }

}
