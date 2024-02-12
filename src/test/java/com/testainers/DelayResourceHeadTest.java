package com.testainers;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.containsStringIgnoringCase;
import static org.hamcrest.Matchers.is;

/**
 * @author Eduardo Folly
 */
@QuarkusTest
public class DelayResourceHeadTest extends BaseResourceTest {

    @Test
    public void testDelayHeadEmpty() {
        base().head("/delay")
              .then()
              .statusCode(404)
              .statusLine(containsStringIgnoringCase("Not Found"));
    }

    @Test
    public void testDelayHeadString() {
        base().head("/delay/a")
              .then()
              .statusCode(404)
              .statusLine(containsStringIgnoringCase("Not Found"));
    }

    @Test
    public void testDelayHeadDouble() {
        base().head("/delay/1.8")
              .then()
              .statusCode(404)
              .statusLine(containsStringIgnoringCase("Not Found"));
    }

    @Test
    public void testDelayHeadNegative() {
        base().head("/delay/-1")
              .then()
              .statusCode(400)
              .body(is(""));
    }

    @Test
    public void testDelayHead0() {
        base().head("/delay/0")
              .then()
              .statusCode(200)
              .body(is(""));
    }

    @Test
    public void testDelayHead1() {
        base().head("/delay/1")
              .then()
              .statusCode(200)
              .body(is(""));
    }

    @Test
    public void testDelayHead10() {
        base().head("/delay/10")
              .then()
              .statusCode(200)
              .body(is(""));
    }

    @Test
    public void testDelayHead11() {
        base().head("/delay/11")
              .then()
              .statusCode(400)
              .body(is(""));
    }

}
