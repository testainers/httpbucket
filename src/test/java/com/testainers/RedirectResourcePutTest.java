package com.testainers;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import jakarta.ws.rs.core.HttpHeaders;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.config.RedirectConfig.redirectConfig;
import static org.hamcrest.Matchers.is;

/**
 * @author Eduardo Folly
 */
@QuarkusTest
public class RedirectResourcePutTest {

    static final String LOCATION = "https://testainers.com";

    static final RestAssuredConfig CONFIG = RestAssured
            .config()
            .redirect(redirectConfig().followRedirects(false));

    @Test
    public void testRedirectPut302() {
        given().config(CONFIG)
               .when()
               .queryParam("url", LOCATION)
               .put("/redirect")
               .then()
               .statusCode(302)
               .header(HttpHeaders.LOCATION, LOCATION)
               .header(HttpHeaders.CONTENT_LENGTH, "0");
    }

    @Test
    public void testRedirectPut303() {
        given().config(CONFIG)
               .when()
               .queryParam("url", LOCATION)
               .queryParam("code", 303)
               .put("/redirect")
               .then()
               .statusCode(303)
               .header(HttpHeaders.LOCATION, LOCATION)
               .header(HttpHeaders.CONTENT_LENGTH, "0");
    }

    @Test
    public void testRedirectPut299() {
        given().config(CONFIG)
               .when()
               .queryParam("url", LOCATION)
               .queryParam("code", 299)
               .put("/redirect")
               .then()
               .statusCode(500)
               .contentType(ContentType.TEXT)
               .body(is("Invalid status code: 299"));
    }

    @Test
    public void testRedirectPut499() {
        given().config(CONFIG)
               .when()
               .queryParam("url", LOCATION)
               .queryParam("code", 499)
               .put("/redirect")
               .then()
               .statusCode(500)
               .contentType(ContentType.TEXT)
               .body(is("Invalid status code: 499"));
    }

    @Test
    public void testRedirectPutNoUrl() {
        given().config(CONFIG)
               .when()
               .put("/redirect")
               .then()
               .statusCode(500)
               .contentType(ContentType.TEXT)
               .body(is("Invalid URL"));
    }

    @Test
    public void testRedirectPutEmptyUrl() {
        given().config(CONFIG)
               .when()
               .queryParam("url", "")
               .put("/redirect")
               .then()
               .statusCode(500)
               .contentType(ContentType.TEXT)
               .body(is("Invalid URL Scheme"));
    }

}