package com.testainers;

import io.quarkus.test.junit.QuarkusTest;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;

/**
 * @author Eduardo Folly
 */
@QuarkusTest
public class StatusResourceGetTest {

    @Test
    public void testMethodsGetString() {
        given().when()
               .get("/status/a")
               .then()
               .statusCode(404)
               .statusLine(containsStringIgnoringCase("Not Found"));
    }

    @Test
    public void testMethodsGetDouble() {
        given().when()
               .get("/status/1.8")
               .then()
               .statusCode(404)
               .statusLine(containsStringIgnoringCase("Not Found"));
    }

    @Test
    public void testMethodsGetNegative() {
        given().when()
               .get("/status/-1")
               .then()
               .statusCode(500)
               .body("method", is("GET"),
                     "body", is("Unknown status code: -1")
               );
    }

    @Test
    public void testMethodsGet0() {
        given().when()
               .get("/status/0")
               .then()
               .statusCode(500)
               .body("method", is("GET"),
                     "body", is("Unknown status code: 0")
               );
    }

    @Test
    public void testMethodsGet99() {
        given().when()
               .get("/status/99")
               .then()
               .statusCode(500)
               .body("method", is("GET"),
                     "body", is("Unknown status code: 99")
               );
    }

    @Test
    public void testMethodsGet100() {
        given().when()
               .get("/status/100")
               .then()
               .statusCode(500)
               .body("method", is("GET"),
                     "body",
                     is("Informational responses are not supported: 100")
               );
    }

    @Test
    public void testMethodsGet199() {
        given().when()
               .get("/status/199")
               .then()
               .statusCode(500)
               .body("method", is("GET"),
                     "body",
                     is("Informational responses are not supported: 199")
               );
    }

    @Test
    public void testMethodsGet200() {
        given().when()
               .headers("test-header", "test-header-value")
               .queryParam("test", "test")
               .get("/status/200")
               .then()
               .statusCode(200)
               .body("method", is("GET"),
                     "queryParameters",
                     is(Map.of("test", List.of("test"))),
                     "headers.test-header", is(List.of("test-header-value"))
               );
    }

    @Test
    public void testMethodsGet204() {
        given().when()
               .headers("test-header", "test-header-value")
               .queryParam("test", "test")
               .get("/status/204")
               .then()
               .statusCode(204)
               .body(is(""));
    }

    @Test
    public void testMethodsGet205() {
        given().when()
               .headers("test-header", "test-header-value")
               .queryParam("test", "test")
               .get("/status/205")
               .then()
               .statusCode(205)
               .headers("content-length", "0")
               .body(is(""));
    }

    @Test
    public void testMethodsGet304() {
        given().when()
               .headers("test-header", "test-header-value")
               .queryParam("test", "test")
               .get("/status/304")
               .then()
               .statusCode(304)
               .body(is(""));
    }

    @Test
    public void testMethodsGet599() {
        given().when()
               .headers("test-header", "test-header-value")
               .queryParam("test", "test")
               .get("/status/599")
               .then()
               .statusCode(599)
               .body("method", is("GET"),
                     "queryParameters",
                     is(Map.of("test", List.of("test"))),
                     "headers.test-header", is(List.of("test-header-value"))
               );
    }

    @Test
    public void testMethodsGet600() {
        given().when()
               .get("/status/600")
               .then()
               .statusCode(500)
               .body("method", is("GET"),
                     "body", is("Unknown status code: 600")
               );
    }

}
