package com.testainers;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.containsStringIgnoringCase;
import static org.hamcrest.Matchers.is;

/**
 * @author Eduardo Folly
 */
@QuarkusTest
public class DelayResourcePostTest extends BaseResourceTest {

    @Test
    public void testDelayPostEmpty() {
        json().post("/delay")
              .then()
              .statusCode(404)
              .statusLine(containsStringIgnoringCase("Not Found"));
    }

    @Test
    public void testDelayPostString() {
        json().post("/delay/a")
              .then()
              .statusCode(404)
              .statusLine(containsStringIgnoringCase("Not Found"));
    }

    @Test
    public void testDelayPostDouble() {
        json().post("/delay/1.8")
              .then()
              .statusCode(404)
              .statusLine(containsStringIgnoringCase("Not Found"));
    }

    @Test
    public void testDelayPostNegative() {
        json().post("/delay/-1")
              .then()
              .statusCode(400)
              .body("body", is("Invalid delay: -1"),
                    bodyMatchers("POST"));
    }

    @Test
    public void testDelayPost0() {
        json().post("/delay/0")
              .then()
              .statusCode(200)
              .body("body", is("Slept for 0 seconds."),
                    bodyMatchers("POST"));
    }

    @Test
    public void testDelayPost1() {
        json().post("/delay/1")
              .then()
              .statusCode(200)
              .body("body", is("Slept for 1 seconds."),
                    bodyMatchers("POST"));
    }

    @Test
    public void testDelayPost10() {
        json().post("/delay/10")
              .then()
              .statusCode(200)
              .body("body", is("Slept for 10 seconds."),
                    bodyMatchers("POST"));
    }

    @Test
    public void testDelayPost11() {
        json().post("/delay/11")
              .then()
              .statusCode(400)
              .body("body", is("Invalid delay: 11"),
                    bodyMatchers("POST"));
    }

}
