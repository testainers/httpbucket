package com.testainers;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.Method;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.containsStringIgnoringCase;
import static org.hamcrest.Matchers.is;

/**
 * @author Eduardo Folly
 */
@QuarkusTest
public class DelayResourceTest extends BaseResourceTest {

    @Test
    public void delayEmpty() {
        for (Method method : METHODS) {
            json(method)
                    .request(method, "/delay")
                    .then()
                    .statusCode(404)
                    .statusLine(containsStringIgnoringCase("Not Found"));
        }
    }

    @Test
    public void delayString() {
        for (Method method : METHODS) {
            json(method)
                    .request(method, "/delay/a")
                    .then()
                    .statusCode(404)
                    .statusLine(containsStringIgnoringCase("Not Found"));
        }
    }

    @Test
    public void delayDouble() {
        for (Method method : METHODS) {
            json(method)
                    .request(method, "/delay/1.8")
                    .then()
                    .statusCode(404)
                    .statusLine(containsStringIgnoringCase("Not Found"));
        }
    }

    @Test
    public void delayNegative() {
        for (Method method : METHODS) {
            json(method)
                    .request(method, "/delay/-1")
                    .then()
                    .statusCode(400)
                    .body("body", is("Invalid delay: -1"),
                          bodyMatchers(method));
        }
    }

    @Test
    public void delay0() {
        for (Method method : METHODS) {
            json(method)
                    .request(method, "/delay/0")
                    .then()
                    .statusCode(200)
                    .body("body", is("Slept for 0 seconds."),
                          bodyMatchers(method));
        }
    }

    @Test
    public void delay1() {
        for (Method method : METHODS) {
            json(method)
                    .request(method, "/delay/1")
                    .then()
                    .statusCode(200)
                    .body("body", is("Slept for 1 seconds."),
                          bodyMatchers(method));
        }
    }

    @Test
    public void delay10() {
        for (Method method : METHODS) {
            json(method)
                    .request(method, "/delay/10")
                    .then()
                    .statusCode(200)
                    .body("body", is("Slept for 10 seconds."),
                          bodyMatchers(method));
        }
    }

    @Test
    public void delay11() {
        for (Method method : METHODS) {
            json(method)
                    .request(method, "/delay/11")
                    .then()
                    .statusCode(400)
                    .body("body", is("Invalid delay: 11"),
                          bodyMatchers(method));
        }
    }

}
