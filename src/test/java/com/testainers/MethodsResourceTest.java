package com.testainers;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

/**
 * @author Eduardo Folly
 */
@QuarkusTest
public class MethodsResourceTest {

    @Test
    public void testMethodsGet() {
        given().when()
               .headers("test-header", "test-header-value")
               .queryParam("test", "test")
               .get("/methods")
               .then()
               .statusCode(200)
               .body("method", is("GET"),
                     "queryParameters",
                     is(Map.of("test", List.of("test"))),
                     "headers.test-header", is(List.of("test-header-value"))
               );
    }

    @Test
    public void testMethodsPost() {
        Map<String, Object> body = Map.of("test_string", "test",
                                          "test_int", 1,
                                          "test_boolean", true);

        given().when()
               .headers("test-header", "test-header-value")
               .queryParam("test", "test")
               .contentType(ContentType.JSON)
               .body(body)
               .post("/methods")
               .then()
               .statusCode(200)
               .body("method", is("POST"),
                     "queryParameters",
                     is(Map.of("test", List.of("test"))),
                     "headers.test-header", is(List.of("test-header-value")),
                     "body", is(body)
               );
    }

    @Test
    public void testMethodsPut() {
        Map<String, Object> body = Map.of("test_string", "test",
                                          "test_int", 1,
                                          "test_boolean", true);

        given().when()
               .headers("test-header", "test-header-value")
               .queryParam("test", "test")
               .contentType(ContentType.JSON)
               .body(body)
               .put("/methods")
               .then()
               .statusCode(200)
               .body("method", is("PUT"),
                     "queryParameters",
                     is(Map.of("test", List.of("test"))),
                     "headers.test-header", is(List.of("test-header-value")),
                     "body", is(body)
               );
    }

    @Test
    public void testMethodsDelete() {
        Map<String, Object> body = Map.of("test_string", "test",
                                          "test_int", 1,
                                          "test_boolean", true);

        given().when()
               .headers("test-header", "test-header-value")
               .queryParam("test", "test")
               .contentType(ContentType.JSON)
               .body(body)
               .delete("/methods")
               .then()
               .statusCode(200)
               .body("method", is("DELETE"),
                     "queryParameters",
                     is(Map.of("test", List.of("test"))),
                     "headers.test-header", is(List.of("test-header-value")),
                     "body", is(body)
               );
    }

}