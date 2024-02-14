package com.testainers;

import io.restassured.RestAssured;
import io.restassured.config.LogConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.specification.RequestSpecification;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.config.RedirectConfig.redirectConfig;
import static org.hamcrest.Matchers.*;

/**
 * @author Eduardo Folly
 */
public class BaseResourceTest {

    protected static final List<Method> METHODS =
            List.of(Method.GET, Method.POST, Method.PUT,
                    Method.PATCH, Method.DELETE);

    protected static final RestAssuredConfig CONFIG = RestAssured
            .config()
            .logConfig(
                    LogConfig
                            .logConfig()
                            .enableLoggingOfRequestAndResponseIfValidationFails()
                            .enablePrettyPrinting(true)
            )
            .redirect(redirectConfig().followRedirects(false));
    protected static final Map<String, Object> HEADERS =
            Map.of("test-header", List.of("test-header-value"),
                   "test-header-2", List.of("test-header-value-2"));

    protected static final Map<String, Object> QUERY_PARAMS =
            Map.of("test-qs", List.of("test-qs"));

    protected static final Map<String, Object> BODY =
            Map.of("test_string", "test",
                   "test_int", 1,
                   "test_boolean", true);

    protected RequestSpecification json(Method method) {
        RequestSpecification spec = given()
                .config(CONFIG)
                .when()
                .headers(HEADERS)
                .queryParams(QUERY_PARAMS)
                .contentType(ContentType.JSON);

        if (method != null && method != Method.GET) {
            spec = spec.body(BODY);
        }

        return spec;
    }

    protected Object[] bodyMatchers(Method method, Object... objects) {
        List<Object> matchers = new ArrayList<>();

        matchers.add("method");
        matchers.add(is(method.name()));

        matchers.add("queryParameters");
        matchers.add(is(QUERY_PARAMS));

        matchers.add("headers");
        matchers.add(aMapWithSize(greaterThanOrEqualTo(HEADERS.size())));

        matchers.add("headers");
        matchers.add(hasEntry(in(HEADERS.keySet()), in(HEADERS.values())));

        if (objects != null && objects.length > 0) {
            matchers.addAll(Arrays.asList(objects));
        }

        return matchers.toArray();
    }

}
