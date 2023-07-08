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
public class StatusResourceDeleteTest {

    private static final Map<String, Object> BODY = Map
            .of("test_string", "test",
                "test_int", 1,
                "test_boolean", true);

    @Test
    public void testMethodsDeleteString() {
        given().when()
               .delete("/status/a")
               .then()
               .statusCode(404)
               .statusLine(containsStringIgnoringCase("Not Found"));
    }

    @Test
    public void testMethodsDeleteDouble() {
        given().when()
               .delete("/status/1.8")
               .then()
               .statusCode(404)
               .statusLine(containsStringIgnoringCase("Not Found"));
    }

    @Test
    public void testMethodsDeleteNegative() {
        given().when()
               .contentType(ContentType.JSON)
               .body(BODY)
               .delete("/status/-1")
               .then()
               .statusCode(500)
               .body("method", is("DELETE"),
                     "body", is("Unknown status code: -1")
               );
    }

    @Test
    public void testMethodsDelete0() {
        given().when()
               .contentType(ContentType.JSON)
               .body(BODY)
               .delete("/status/0")
               .then()
               .statusCode(500)
               .body("method", is("DELETE"),
                     "body", is("Unknown status code: 0")
               );
    }

    @Test
    public void testMethodsDelete99() {
        given().when()
               .contentType(ContentType.JSON)
               .body(BODY)
               .delete("/status/99")
               .then()
               .statusCode(500)
               .body("method", is("DELETE"),
                     "body", is("Unknown status code: 99")
               );
    }

    @Test
    public void testMethodsDelete100() {
        given().when()
               .contentType(ContentType.JSON)
               .body(BODY)
               .delete("/status/100")
               .then()
               .statusCode(500)
               .body("method", is("DELETE"),
                     "body",
                     is("Informational responses are not supported: 100")
               );
    }

    @Test
    public void testMethodsDelete199() {
        given().when()
               .contentType(ContentType.JSON)
               .body(BODY)
               .delete("/status/199")
               .then()
               .statusCode(500)
               .body("method", is("DELETE"),
                     "body",
                     is("Informational responses are not supported: 199")
               );
    }

    @Test
    public void testMethodsDelete200() {
        given().when()
               .headers("test-header", "test-header-value")
               .queryParam("test", "test")
               .contentType(ContentType.JSON)
               .body(BODY)
               .delete("/status/200")
               .then()
               .statusCode(200)
               .body("method", is("DELETE"),
                     "queryParameters",
                     is(Map.of("test", List.of("test"))),
                     "headers.test-header", is(List.of("test-header-value")),
                     "body", is(BODY)
               );
    }

    @Test
    public void testMethodsDelete204() {
        given().when()
               .headers("test-header", "test-header-value")
               .queryParam("test", "test")
               .contentType(ContentType.JSON)
               .body(BODY)
               .delete("/status/204")
               .then()
               .statusCode(204)
               .body(is(""));
    }

    @Test
    public void testMethodsDelete205() {
        given().when()
               .headers("test-header", "test-header-value")
               .queryParam("test", "test")
               .contentType(ContentType.JSON)
               .body(BODY)
               .delete("/status/205")
               .then()
               .statusCode(205)
               .headers("content-length", "0")
               .body(is(""));
    }

    @Test
    public void testMethodsDelete304() {
        given().when()
               .headers("test-header", "test-header-value")
               .queryParam("test", "test")
               .contentType(ContentType.JSON)
               .body(BODY)
               .delete("/status/304")
               .then()
               .statusCode(304)
               .body(is(""));
    }

    @Test
    public void testMethodsDelete599() {
        given().when()
               .headers("test-header", "test-header-value")
               .queryParam("test", "test")
               .contentType(ContentType.JSON)
               .body(BODY)
               .delete("/status/599")
               .then()
               .statusCode(599)
               .body("method", is("DELETE"),
                     "queryParameters",
                     is(Map.of("test", List.of("test"))),
                     "headers.test-header", is(List.of("test-header-value")),
                     "body", is(BODY)

               );
    }

    @Test
    public void testMethodsDelete600() {
        given().when()
               .contentType(ContentType.JSON)
               .body(BODY)
               .delete("/status/600")
               .then()
               .statusCode(500)
               .body("method", is("DELETE"),
                     "body", is("Unknown status code: 600")
               );
    }

}
