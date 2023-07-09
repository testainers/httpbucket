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
public class RedirectResourcePostTest {

    static final String LOCATION = "https://testainers.com";

    static final RestAssuredConfig CONFIG = RestAssured
            .config()
            .redirect(redirectConfig().followRedirects(false));

    @Test
    public void testRedirectPost302() {
        given().config(CONFIG)
               .when()
               .queryParam("url", LOCATION)
               .post("/redirect")
               .then()
               .statusCode(302)
               .header(HttpHeaders.LOCATION, LOCATION)
               .header(HttpHeaders.CONTENT_LENGTH, "0");
    }

    @Test
    public void testRedirectPost303() {
        given().config(CONFIG)
               .when()
               .queryParam("url", LOCATION)
               .queryParam("code", 303)
               .post("/redirect")
               .then()
               .statusCode(303)
               .header(HttpHeaders.LOCATION, LOCATION)
               .header(HttpHeaders.CONTENT_LENGTH, "0");
    }

    @Test
    public void testRedirectPost299() {
        given().config(CONFIG)
               .when()
               .queryParam("url", LOCATION)
               .queryParam("code", 299)
               .post("/redirect")
               .then()
               .statusCode(500)
               .contentType(ContentType.TEXT)
               .body(is("Invalid status code: 299"));
    }

    @Test
    public void testRedirectPost499() {
        given().config(CONFIG)
               .when()
               .queryParam("url", LOCATION)
               .queryParam("code", 499)
               .post("/redirect")
               .then()
               .statusCode(500)
               .contentType(ContentType.TEXT)
               .body(is("Invalid status code: 499"));
    }

    @Test
    public void testRedirectPostNoUrl() {
        given().config(CONFIG)
               .when()
               .post("/redirect")
               .then()
               .statusCode(500)
               .contentType(ContentType.TEXT)
               .body(is("Invalid URL"));
    }

    @Test
    public void testRedirectPostEmptyUrl() {
        given().config(CONFIG)
               .when()
               .queryParam("url", "")
               .post("/redirect")
               .then()
               .statusCode(500)
               .contentType(ContentType.TEXT)
               .body(is("Invalid URL Scheme"));
    }

}