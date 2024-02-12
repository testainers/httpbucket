package com.testainers;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.containsStringIgnoringCase;
import static org.hamcrest.Matchers.is;

/**
 * @author Eduardo Folly
 */
@QuarkusTest
public class DelayResourcePutTest extends BaseResourceTest {

    @Test
    public void testDelayPutEmpty() {
        json().put("/delay")
              .then()
              .statusCode(404)
              .statusLine(containsStringIgnoringCase("Not Found"));
    }

    @Test
    public void testDelayPutString() {
        json().put("/delay/a")
              .then()
              .statusCode(404)
              .statusLine(containsStringIgnoringCase("Not Found"));
    }

    @Test
    public void testDelayPutDouble() {
        json().put("/delay/1.8")
              .then()
              .statusCode(404)
              .statusLine(containsStringIgnoringCase("Not Found"));
    }

    @Test
    public void testDelayPutNegative() {
        json().put("/delay/-1")
              .then()
              .statusCode(400)
              .body("body", is("Invalid delay: -1"),
                    bodyMatchers("PUT"));
    }

    @Test
    public void testDelayPut0() {
        json().put("/delay/0")
              .then()
              .statusCode(200)
              .body("body", is("Slept for 0 seconds."),
                    bodyMatchers("PUT"));
    }

    @Test
    public void testDelayPut1() {
        json().put("/delay/1")
              .then()
              .statusCode(200)
              .body("body", is("Slept for 1 seconds."),
                    bodyMatchers("PUT"));
    }

    @Test
    public void testDelayPut10() {
        json().put("/delay/10")
              .then()
              .statusCode(200)
              .body("body", is("Slept for 10 seconds."),
                    bodyMatchers("PUT"));
    }

    @Test
    public void testDelayPut11() {
        json().put("/delay/11")
              .then()
              .statusCode(400)
              .body("body", is("Invalid delay: 11"),
                    bodyMatchers("PUT"));
    }

}
