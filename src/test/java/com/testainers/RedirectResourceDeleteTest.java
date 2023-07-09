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
public class RedirectResourceDeleteTest {

    static final String LOCATION = "https://testainers.com";

    static final RestAssuredConfig CONFIG = RestAssured
            .config()
            .redirect(redirectConfig().followRedirects(false));

    @Test
    public void testRedirectDelete302() {
        given().config(CONFIG)
               .when()
               .queryParam("url", LOCATION)
               .delete("/redirect")
               .then()
               .statusCode(302)
               .header(HttpHeaders.LOCATION, LOCATION)
               .header(HttpHeaders.CONTENT_LENGTH, "0");
    }

    @Test
    public void testRedirectDelete303() {
        given().config(CONFIG)
               .when()
               .queryParam("url", LOCATION)
               .queryParam("code", 303)
               .delete("/redirect")
               .then()
               .statusCode(303)
               .header(HttpHeaders.LOCATION, LOCATION)
               .header(HttpHeaders.CONTENT_LENGTH, "0");
    }

    @Test
    public void testRedirectDelete299() {
        given().config(CONFIG)
               .when()
               .queryParam("url", LOCATION)
               .queryParam("code", 299)
               .delete("/redirect")
               .then()
               .statusCode(500)
               .contentType(ContentType.TEXT)
               .body(is("Invalid status code: 299"));
    }

    @Test
    public void testRedirectDelete499() {
        given().config(CONFIG)
               .when()
               .queryParam("url", LOCATION)
               .queryParam("code", 499)
               .delete("/redirect")
               .then()
               .statusCode(500)
               .contentType(ContentType.TEXT)
               .body(is("Invalid status code: 499"));
    }

    @Test
    public void testRedirectDeleteNoUrl() {
        given().config(CONFIG)
               .when()
               .delete("/redirect")
               .then()
               .statusCode(500)
               .contentType(ContentType.TEXT)
               .body(is("Invalid URL"));
    }

    @Test
    public void testRedirectDeleteEmptyUrl() {
        given().config(CONFIG)
               .when()
               .queryParam("url", "")
               .delete("/redirect")
               .then()
               .statusCode(500)
               .contentType(ContentType.TEXT)
               .body(is("Invalid URL Scheme"));
    }

}