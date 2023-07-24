package com.testainers;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.containsStringIgnoringCase;
import static org.hamcrest.Matchers.is;

/**
 * @author Eduardo Folly
 */
@QuarkusTest
public class DelayResourceDeleteTest extends BaseResourceTest {

    @Test
    public void testDelayDeleteEmpty() {
        json().delete("/delay")
              .then()
              .statusCode(404)
              .statusLine(containsStringIgnoringCase("Not Found"));
    }

    @Test
    public void testDelayDeleteString() {
        json().delete("/delay/a")
              .then()
              .statusCode(404)
              .statusLine(containsStringIgnoringCase("Not Found"));
    }

    @Test
    public void testDelayDeleteDouble() {
        json().delete("/delay/1.8")
              .then()
              .statusCode(404)
              .statusLine(containsStringIgnoringCase("Not Found"));
    }

    @Test
    public void testDelayDeleteNegative() {
        json().delete("/delay/-1")
              .then()
              .statusCode(400)
              .body("body", is("Invalid delay: -1"),
                    bodyMatchers("DELETE"));
    }

    @Test
    public void testDelayDelete0() {
        json().delete("/delay/0")
              .then()
              .statusCode(200)
              .body("body", is("Slept for 0 seconds."),
                    bodyMatchers("DELETE"));
    }

    @Test
    public void testDelayDelete1() {
        json().delete("/delay/1")
              .then()
              .statusCode(200)
              .body("body", is("Slept for 1 seconds."),
                    bodyMatchers("DELETE"));
    }

    @Test
    public void testDelayDelete10() {
        json().delete("/delay/10")
              .then()
              .statusCode(200)
              .body("body", is("Slept for 10 seconds."),
                    bodyMatchers("DELETE"));
    }

    @Test
    public void testDelayDelete11() {
        json().delete("/delay/11")
              .then()
              .statusCode(400)
              .body("body", is("Invalid delay: 11"),
                    bodyMatchers("DELETE"));
    }

}
