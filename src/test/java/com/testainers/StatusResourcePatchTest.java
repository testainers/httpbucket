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
public class StatusResourcePatchTest {

    private static final Map<String, Object> BODY = Map
            .of("test_string", "test",
                "test_int", 1,
                "test_boolean", true);

    @Test
    public void testStatusPatchString() {
        given().when()
               .patch("/status/a")
               .then()
               .statusCode(404)
               .statusLine(containsStringIgnoringCase("Not Found"));
    }

    @Test
    public void testStatusPatchDouble() {
        given().when()
               .patch("/status/1.8")
               .then()
               .statusCode(404)
               .statusLine(containsStringIgnoringCase("Not Found"));
    }

    @Test
    public void testStatusPatchNegative() {
        given().when()
               .contentType(ContentType.JSON)
               .body(BODY)
               .patch("/status/-1")
               .then()
               .statusCode(500)
               .body("method", is("PATCH"),
                     "body", is("Unknown status code: -1")
               );
    }

    @Test
    public void testStatusPatch0() {
        given().when()
               .contentType(ContentType.JSON)
               .body(BODY)
               .patch("/status/0")
               .then()
               .statusCode(500)
               .body("method", is("PATCH"),
                     "body", is("Unknown status code: 0")
               );
    }

    @Test
    public void testStatusPatch99() {
        given().when()
               .contentType(ContentType.JSON)
               .body(BODY)
               .patch("/status/99")
               .then()
               .statusCode(500)
               .body("method", is("PATCH"),
                     "body", is("Unknown status code: 99")
               );
    }

    @Test
    public void testStatusPatch100() {
        given().when()
               .contentType(ContentType.JSON)
               .body(BODY)
               .patch("/status/100")
               .then()
               .statusCode(500)
               .body("method", is("PATCH"),
                     "body",
                     is("Informational responses are not supported: 100")
               );
    }

    @Test
    public void testStatusPatch199() {
        given().when()
               .contentType(ContentType.JSON)
               .body(BODY)
               .patch("/status/199")
               .then()
               .statusCode(500)
               .body("method", is("PATCH"),
                     "body",
                     is("Informational responses are not supported: 199")
               );
    }

    @Test
    public void testStatusPatch200() {
        given().when()
               .headers("test-header", "test-header-value")
               .queryParam("test", "test")
               .contentType(ContentType.JSON)
               .body(BODY)
               .patch("/status/200")
               .then()
               .statusCode(200)
               .body("method", is("PATCH"),
                     "queryParameters",
                     is(Map.of("test", List.of("test"))),
                     "headers.test-header", is(List.of("test-header-value")),
                     "body", is(BODY)
               );
    }

    @Test
    public void testStatusPatch204() {
        given().when()
               .headers("test-header", "test-header-value")
               .queryParam("test", "test")
               .contentType(ContentType.JSON)
               .body(BODY)
               .patch("/status/204")
               .then()
               .statusCode(204)
               .body(is(""));
    }

    @Test
    public void testStatusPatch205() {
        given().when()
               .headers("test-header", "test-header-value")
               .queryParam("test", "test")
               .contentType(ContentType.JSON)
               .body(BODY)
               .patch("/status/205")
               .then()
               .statusCode(205)
               .headers("content-length", "0")
               .body(is(""));
    }

    @Test
    public void testStatusPatch304() {
        given().when()
               .headers("test-header", "test-header-value")
               .queryParam("test", "test")
               .contentType(ContentType.JSON)
               .body(BODY)
               .patch("/status/304")
               .then()
               .statusCode(304)
               .body(is(""));
    }

    @Test
    public void testStatusPatch599() {
        given().when()
               .headers("test-header", "test-header-value")
               .queryParam("test", "test")
               .contentType(ContentType.JSON)
               .body(BODY)
               .patch("/status/599")
               .then()
               .statusCode(599)
               .body("method", is("PATCH"),
                     "queryParameters",
                     is(Map.of("test", List.of("test"))),
                     "headers.test-header", is(List.of("test-header-value")),
                     "body", is(BODY)

               );
    }

    @Test
    public void testStatusPatch600() {
        given().when()
               .contentType(ContentType.JSON)
               .body(BODY)
               .patch("/status/600")
               .then()
               .statusCode(500)
               .body("method", is("PATCH"),
                     "body", is("Unknown status code: 600")
               );
    }

}
