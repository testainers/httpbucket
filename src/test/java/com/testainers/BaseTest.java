package com.testainers;

import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

/**
 * @author Eduardo Folly
 */
public class BaseTest {

    protected static final Map<String, Object> HEADERS =
            Map.of("test-header", List.of("test-header-value"),
                   "test-header-2", List.of("test-header-value-2"));

    protected static final Map<String, Object> QUERY_PARAMS =
            Map.of("test-qs", List.of("test-qs"));

    protected static final Map<String, Object> BODY = Map
            .of("test_string", "test",
                "test_int", 1,
                "test_boolean", true);

    protected RequestSpecification base() {
        return given().when()
                      .headers(HEADERS)
                      .queryParams(QUERY_PARAMS);

    }

    protected RequestSpecification json() {
        return base()
                .contentType(ContentType.JSON)
                .body(BODY);
    }

    protected Object[] bodyMatchers(String method) {
        return new Object[]{"method", is(method),
                "queryParameters", is(QUERY_PARAMS),
                "headers", aMapWithSize(greaterThanOrEqualTo(HEADERS.size())),
                "headers", hasEntry(in(HEADERS.keySet()), in(HEADERS.values())),
        };
    }

}
