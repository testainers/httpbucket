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

    private static final Map<String, Object> BODY = Map
            .of("test_string", "test",
                "test_int", 1,
                "test_boolean", true);

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
        given().when()
               .headers("test-header", "test-header-value")
               .queryParam("test", "test")
               .contentType(ContentType.JSON)
               .body(BODY)
               .post("/methods")
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
    public void testMethodsPut() {
        given().when()
               .headers("test-header", "test-header-value")
               .queryParam("test", "test")
               .contentType(ContentType.JSON)
               .body(BODY)
               .put("/methods")
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
    public void testMethodsPatch() {
        given().when()
               .headers("test-header", "test-header-value")
               .queryParam("test", "test")
               .contentType(ContentType.JSON)
               .body(BODY)
               .patch("/methods")
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
    public void testMethodsDelete() {
        given().when()
               .headers("test-header", "test-header-value")
               .queryParam("test", "test")
               .contentType(ContentType.JSON)
               .body(BODY)
               .delete("/methods")
               .then()
               .statusCode(200)
               .body("method", is("DELETE"),
                     "queryParameters",
                     is(Map.of("test", List.of("test"))),
                     "headers.test-header", is(List.of("test-header-value")),
                     "body", is(BODY)
               );
    }

}