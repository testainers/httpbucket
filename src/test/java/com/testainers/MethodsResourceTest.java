package com.testainers;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;

/**
 * @author Eduardo Folly
 */
@QuarkusTest
public class MethodsResourceTest extends BaseTest {

    @Test
    public void testMethodsGet() {
        base().get("/methods")
              .then()
              .statusCode(200)
              .body("method", is("GET"),
                    "queryParameters", is(QUERY_PARAMS),
                    "headers",
                    aMapWithSize(greaterThanOrEqualTo(HEADERS.size())),
                    "headers",
                    hasEntry(in(HEADERS.keySet()), in(HEADERS.values()))
              );

    }

    @Test
    public void testMethodsHead() {
        base().head("/methods")
              .then()
              .statusCode(200);
    }

    @Test
    public void testMethodsPost() {
        json().post("/methods")
              .then()
              .statusCode(200)
              .body("method", is("POST"),
                    "queryParameters", is(QUERY_PARAMS),
                    "headers",
                    aMapWithSize(greaterThanOrEqualTo(HEADERS.size())),
                    "headers",
                    hasEntry(in(HEADERS.keySet()), in(HEADERS.values())),
                    "body", is(BODY)
              );
    }

    @Test
    public void testMethodsPut() {
        json().put("/methods")
              .then()
              .statusCode(200)
              .body("method", is("PUT"),
                    "queryParameters", is(QUERY_PARAMS),
                    "headers",
                    aMapWithSize(greaterThanOrEqualTo(HEADERS.size())),
                    "headers",
                    hasEntry(in(HEADERS.keySet()), in(HEADERS.values())),
                    "body", is(BODY)
              );
    }

    @Test
    public void testMethodsPatch() {
        json().patch("/methods")
              .then()
              .statusCode(200)
              .body("method", is("PATCH"),
                    "queryParameters", is(QUERY_PARAMS),
                    "headers",
                    aMapWithSize(greaterThanOrEqualTo(HEADERS.size())),
                    "headers",
                    hasEntry(in(HEADERS.keySet()), in(HEADERS.values())),
                    "body", is(BODY)
              );
    }

    @Test
    public void testMethodsDelete() {
        json().delete("/methods")
              .then()
              .statusCode(200)
              .body("method", is("DELETE"),
                    "queryParameters", is(QUERY_PARAMS),
                    "headers",
                    aMapWithSize(greaterThanOrEqualTo(HEADERS.size())),
                    "headers",
                    hasEntry(in(HEADERS.keySet()), in(HEADERS.values())),
                    "body", is(BODY)
              );
    }

}