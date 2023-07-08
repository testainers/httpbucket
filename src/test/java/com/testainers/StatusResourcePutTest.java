package com.testainers;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsStringIgnoringCase;
import static org.hamcrest.CoreMatchers.is;

/**
 * @author Eduardo Folly
 */
@QuarkusTest
public class StatusResourcePutTest {

    private static final Map<String, Object> BODY = Map
            .of("test_string", "test",
                "test_int", 1,
                "test_boolean", true);

    @Test
    public void testMethodsPutString() {
        given().when()
               .put("/status/a")
               .then()
               .statusCode(404)
               .statusLine(containsStringIgnoringCase("Not Found"));
    }

    @Test
    public void testMethodsPutDouble() {
        given().when()
               .put("/status/1.8")
               .then()
               .statusCode(404)
               .statusLine(containsStringIgnoringCase("Not Found"));
    }

    @Test
    public void testMethodsPutNegative() {
        given().when()
               .contentType(ContentType.JSON)
               .body(BODY)
               .put("/status/-1")
               .then()
               .statusCode(500)
               .body("method", is("PUT"),
                     "body", is("Unknown status code: -1")
               );
    }

    @Test
    public void testMethodsPut0() {
        given().when()
               .contentType(ContentType.JSON)
               .body(BODY)
               .put("/status/0")
               .then()
               .statusCode(500)
               .body("method", is("PUT"),
                     "body", is("Unknown status code: 0")
               );
    }

    @Test
    public void testMethodsPut99() {
        given().when()
               .contentType(ContentType.JSON)
               .body(BODY)
               .put("/status/99")
               .then()
               .statusCode(500)
               .body("method", is("PUT"),
                     "body", is("Unknown status code: 99")
               );
    }

    @Test
    public void testMethodsPut100() {
        given().when()
               .contentType(ContentType.JSON)
               .body(BODY)
               .put("/status/100")
               .then()
               .statusCode(500)
               .body("method", is("PUT"),
                     "body",
                     is("Informational responses are not supported: 100")
               );
    }

    @Test
    public void testMethodsPut199() {
        given().when()
               .contentType(ContentType.JSON)
               .body(BODY)
               .put("/status/199")
               .then()
               .statusCode(500)
               .body("method", is("PUT"),
                     "body",
                     is("Informational responses are not supported: 199")
               );
    }

    @Test
    public void testMethodsPut200() {
        given().when()
               .headers("test-header", "test-header-value")
               .queryParam("test", "test")
               .contentType(ContentType.JSON)
               .body(BODY)
               .put("/status/200")
               .then()
               .statusCode(200)
               .body("method", is("PUT"),
                     "queryParameters",
                     is(Map.of("test", List.of("test"))),
                     "headers.test-header", is(List.of("test-header-value")),
                     "body", is(BODY)
               );
    }

    @Test
    public void testMethodsPut204() {
        given().when()
               .headers("test-header", "test-header-value")
               .queryParam("test", "test")
               .contentType(ContentType.JSON)
               .body(BODY)
               .put("/status/204")
               .then()
               .statusCode(204)
               .body(is(""));
    }

    @Test
    public void testMethodsPut205() {
        given().when()
               .headers("test-header", "test-header-value")
               .queryParam("test", "test")
               .contentType(ContentType.JSON)
               .body(BODY)
               .put("/status/205")
               .then()
               .statusCode(205)
               .headers("content-length", "0")
               .body(is(""));
    }

    @Test
    public void testMethodsPut304() {
        given().when()
               .headers("test-header", "test-header-value")
               .queryParam("test", "test")
               .contentType(ContentType.JSON)
               .body(BODY)
               .put("/status/304")
               .then()
               .statusCode(304)
               .body(is(""));
    }

    @Test
    public void testMethodsPut599() {
        given().when()
               .headers("test-header", "test-header-value")
               .queryParam("test", "test")
               .contentType(ContentType.JSON)
               .body(BODY)
               .put("/status/599")
               .then()
               .statusCode(599)
               .body("method", is("PUT"),
                     "queryParameters",
                     is(Map.of("test", List.of("test"))),
                     "headers.test-header", is(List.of("test-header-value")),
                     "body", is(BODY)

               );
    }

    @Test
    public void testMethodsPut600() {
        given().when()
               .contentType(ContentType.JSON)
               .body(BODY)
               .put("/status/600")
               .then()
               .statusCode(500)
               .body("method", is("PUT"),
                     "body", is("Unknown status code: 600")
               );
    }

}
