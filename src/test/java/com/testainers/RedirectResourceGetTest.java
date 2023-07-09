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
public class RedirectResourceGetTest {

    static final String LOCATION = "https://testainers.com";

    static final RestAssuredConfig CONFIG = RestAssured
            .config()
            .redirect(redirectConfig().followRedirects(false));

    @Test
    public void testRedirectGet302() {
        given().config(CONFIG)
               .when()
               .queryParam("url", LOCATION)
               .get("/redirect")
               .then()
               .statusCode(302)
               .header(HttpHeaders.LOCATION, LOCATION)
               .header(HttpHeaders.CONTENT_LENGTH, "0");
    }

    @Test
    public void testRedirectGet303() {
        given().config(CONFIG)
               .when()
               .queryParam("url", LOCATION)
               .queryParam("code", 303)
               .get("/redirect")
               .then()
               .statusCode(303)
               .header(HttpHeaders.LOCATION, LOCATION)
               .header(HttpHeaders.CONTENT_LENGTH, "0");
    }

    @Test
    public void testRedirectGet299() {
        given().config(CONFIG)
               .when()
               .queryParam("url", LOCATION)
               .queryParam("code", 299)
               .get("/redirect")
               .then()
               .statusCode(500)
               .contentType(ContentType.TEXT)
               .body(is("Invalid status code: 299"));
    }

    @Test
    public void testRedirectGet499() {
        given().config(CONFIG)
               .when()
               .queryParam("url", LOCATION)
               .queryParam("code", 499)
               .get("/redirect")
               .then()
               .statusCode(500)
               .contentType(ContentType.TEXT)
               .body(is("Invalid status code: 499"));
    }

    @Test
    public void testRedirectGetNoUrl() {
        given().config(CONFIG)
               .when()
               .get("/redirect")
               .then()
               .statusCode(500)
               .contentType(ContentType.TEXT)
               .body(is("Invalid URL"));
    }

    @Test
    public void testRedirectGetEmptyUrl() {
        given().config(CONFIG)
               .when()
               .queryParam("url", "")
               .get("/redirect")
               .then()
               .statusCode(500)
               .contentType(ContentType.TEXT)
               .body(is("Invalid URL Scheme"));
    }

}