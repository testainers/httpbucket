package com.testainers;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import jakarta.ws.rs.core.HttpHeaders;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.config.RedirectConfig.redirectConfig;
import static org.hamcrest.Matchers.is;

/**
 * @author Eduardo Folly
 */
@QuarkusTest
public class RedirectResourceTest {

    static final String LOCATION = "https://testainers.com";

    static final String INVALID_SCHEME = "testainers.com";

    static final List<Method> METHODS =
            List.of(Method.GET, Method.POST, Method.PUT,
                    Method.PATCH, Method.DELETE);

    static final RestAssuredConfig CONFIG = RestAssured
            .config()
            .redirect(redirectConfig().followRedirects(false));

    @Test
    public void redirect302() {
        for (Method method : METHODS) {
            given().config(CONFIG)
                   .when()
                   .queryParam("url", LOCATION)
                   .request(method, "/redirect")
                   .then()
                   .statusCode(302)
                   .header(HttpHeaders.LOCATION, LOCATION)
                   .header(HttpHeaders.CONTENT_LENGTH, "0");
        }
    }

    @Test
    public void redirect303() {
        for (Method method : METHODS) {
            given().config(CONFIG)
                   .when()
                   .queryParam("url", LOCATION)
                   .queryParam("code", 303)
                   .request(method, "/redirect")
                   .then()
                   .statusCode(303)
                   .header(HttpHeaders.LOCATION, LOCATION)
                   .header(HttpHeaders.CONTENT_LENGTH, "0");
        }
    }

    @Test
    public void redirect299() {
        for (Method method : METHODS) {
            given().config(CONFIG)
                   .when()
                   .queryParam("url", LOCATION)
                   .queryParam("code", 299)
                   .request(method, "/redirect")
                   .then()
                   .statusCode(500)
                   .contentType(ContentType.TEXT)
                   .body(is("Invalid status code: 299"));
        }
    }

    @Test
    public void redirect499() {
        for (Method method : METHODS) {
            given().config(CONFIG)
                   .when()
                   .queryParam("url", LOCATION)
                   .queryParam("code", 499)
                   .request(method, "/redirect")
                   .then()
                   .statusCode(500)
                   .contentType(ContentType.TEXT)
                   .body(is("Invalid status code: 499"));
        }
    }

    @Test
    public void redirectNoUrl() {
        for (Method method : METHODS) {
            given().config(CONFIG)
                   .when()
                   .request(method, "/redirect")
                   .then()
                   .statusCode(500)
                   .contentType(ContentType.TEXT)
                   .body(is("Invalid URL: null"));
        }
    }

    @Test
    public void redirectEmptyUrl() {
        for (Method method : METHODS) {
            given().config(CONFIG)
                   .when()
                   .queryParam("url", "")
                   .request(method, "/redirect")
                   .then()
                   .statusCode(500)
                   .contentType(ContentType.TEXT)
                   .body(is("Invalid URL Scheme: "));
        }
    }

    @Test
    public void redirectInvalidScheme() {
        for (Method method : METHODS) {
            given().config(CONFIG)
                   .when()
                   .queryParam("url", INVALID_SCHEME)
                   .request(method, "/redirect")
                   .then()
                   .statusCode(500)
                   .contentType(ContentType.TEXT)
                   .body(is("Invalid URL Scheme: " + INVALID_SCHEME));
        }
    }

}