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
public class StatusResourcePostTest {

    private static final Map<String, Object> BODY = Map
            .of("test_string", "test",
                "test_int", 1,
                "test_boolean", true);

    @Test
    public void testMethodsPostString() {
        given().when()
               .post("/status/a")
               .then()
               .statusCode(404)
               .statusLine(containsStringIgnoringCase("Not Found"));
    }

    @Test
    public void testMethodsPostDouble() {
        given().when()
               .post("/status/1.8")
               .then()
               .statusCode(404)
               .statusLine(containsStringIgnoringCase("Not Found"));
    }

    @Test
    public void testMethodsPostNegative() {
        given().when()
               .contentType(ContentType.JSON)
               .body(BODY)
               .post("/status/-1")
               .then()
               .statusCode(500)
               .body("method", is("POST"),
                     "body", is("Unknown status code: -1")
               );
    }

    @Test
    public void testMethodsPost0() {
        given().when()
               .contentType(ContentType.JSON)
               .body(BODY)
               .post("/status/0")
               .then()
               .statusCode(500)
               .body("method", is("POST"),
                     "body", is("Unknown status code: 0")
               );
    }

    @Test
    public void testMethodsPost99() {
        given().when()
               .contentType(ContentType.JSON)
               .body(BODY)
               .post("/status/99")
               .then()
               .statusCode(500)
               .body("method", is("POST"),
                     "body", is("Unknown status code: 99")
               );
    }

    @Test
    public void testMethodsPost100() {
        given().when()
               .contentType(ContentType.JSON)
               .body(BODY)
               .post("/status/100")
               .then()
               .statusCode(500)
               .body("method", is("POST"),
                     "body",
                     is("Informational responses are not supported: 100")
               );
    }

    @Test
    public void testMethodsPost199() {
        given().when()
               .contentType(ContentType.JSON)
               .body(BODY)
               .post("/status/199")
               .then()
               .statusCode(500)
               .body("method", is("POST"),
                     "body",
                     is("Informational responses are not supported: 199")
               );
    }

    @Test
    public void testMethodsPost200() {
        given().when()
               .headers("test-header", "test-header-value")
               .queryParam("test", "test")
               .contentType(ContentType.JSON)
               .body(BODY)
               .post("/status/200")
               .then()
               .statusCode(200)
               .body("method", is("POST"),
                     "queryParameters",
                     is(Map.of("test", List.of("test"))),
                     "headers.test-header", is(List.of("test-header-value")),
                     "body", is(BODY)
               );
    }

    @Test
    public void testMethodsPost204() {
        given().when()
               .headers("test-header", "test-header-value")
               .queryParam("test", "test")
               .contentType(ContentType.JSON)
               .body(BODY)
               .post("/status/204")
               .then()
               .statusCode(204)
               .body(is(""));
    }

    @Test
    public void testMethodsPost205() {
        given().when()
               .headers("test-header", "test-header-value")
               .queryParam("test", "test")
               .contentType(ContentType.JSON)
               .body(BODY)
               .post("/status/205")
               .then()
               .statusCode(205)
               .headers("content-length", "0")
               .body(is(""));
    }

    @Test
    public void testMethodsPost304() {
        given().when()
               .headers("test-header", "test-header-value")
               .queryParam("test", "test")
               .contentType(ContentType.JSON)
               .body(BODY)
               .post("/status/304")
               .then()
               .statusCode(304)
               .body(is(""));
    }

    @Test
    public void testMethodsPost599() {
        given().when()
               .headers("test-header", "test-header-value")
               .queryParam("test", "test")
               .contentType(ContentType.JSON)
               .body(BODY)
               .post("/status/599")
               .then()
               .statusCode(599)
               .body("method", is("POST"),
                     "queryParameters",
                     is(Map.of("test", List.of("test"))),
                     "headers.test-header", is(List.of("test-header-value")),
                     "body", is(BODY)

               );
    }

    @Test
    public void testMethodsPost600() {
        given().when()
               .contentType(ContentType.JSON)
               .body(BODY)
               .post("/status/600")
               .then()
               .statusCode(500)
               .body("method", is("POST"),
                     "body", is("Unknown status code: 600")
               );
    }

}
