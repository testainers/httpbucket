package com.testainers;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.containsStringIgnoringCase;
import static org.hamcrest.Matchers.is;

/**
 * @author Eduardo Folly
 */
@QuarkusTest
public class DelayResourcePatchTest extends BaseResourceTest {

    @Test
    public void testDelayPatchEmpty() {
        json().patch("/delay")
              .then()
              .statusCode(404)
              .statusLine(containsStringIgnoringCase("Not Found"));
    }

    @Test
    public void testDelayPatchString() {
        json().patch("/delay/a")
              .then()
              .statusCode(404)
              .statusLine(containsStringIgnoringCase("Not Found"));
    }

    @Test
    public void testDelayPatchDouble() {
        json().patch("/delay/1.8")
              .then()
              .statusCode(404)
              .statusLine(containsStringIgnoringCase("Not Found"));
    }

    @Test
    public void testDelayPatchNegative() {
        json().patch("/delay/-1")
              .then()
              .statusCode(400)
              .body("body", is("Invalid delay: -1"),
                    bodyMatchers("PATCH"));
    }

    @Test
    public void testDelayPatch0() {
        json().patch("/delay/0")
              .then()
              .statusCode(200)
              .body("body", is("Slept for 0 seconds."),
                    bodyMatchers("PATCH"));
    }

    @Test
    public void testDelayPatch1() {
        json().patch("/delay/1")
              .then()
              .statusCode(200)
              .body("body", is("Slept for 1 seconds."),
                    bodyMatchers("PATCH"));
    }

    @Test
    public void testDelayPatch10() {
        json().patch("/delay/10")
              .then()
              .statusCode(200)
              .body("body", is("Slept for 10 seconds."),
                    bodyMatchers("PATCH"));
    }

    @Test
    public void testDelayPatch11() {
        json().patch("/delay/11")
              .then()
              .statusCode(400)
              .body("body", is("Invalid delay: 11"),
                    bodyMatchers("PATCH"));
    }

}
