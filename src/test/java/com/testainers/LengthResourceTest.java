package com.testainers;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import jakarta.ws.rs.core.MediaType;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

/**
 * @author Eduardo Folly
 */
@QuarkusTest
public class LengthResourceTest {

    static final List<Method> METHODS =
            List.of(Method.GET, Method.POST, Method.PUT,
                    Method.PATCH, Method.DELETE);

    @Test
    public void length0() {
        for (Method method : METHODS) {
            given().when()
                   .request(method, "/length/0")
                   .then()
                   .statusCode(500)
                   .contentType(ContentType.TEXT)
                   .body(is("Invalid size: 0"));
        }
    }

    @Test
    public void length5() {
        for (Method method : METHODS) {
            given().when()
                   .request(method, "/length/5")
                   .then()
                   .statusCode(200)
                   .contentType(ContentType.TEXT)
                   .header("content-length", "5")
                   .body(is("00000"));
        }
    }

    @Test
    public void length10() {
        for (Method method : METHODS) {
            given().when()
                   .accept(MediaType.TEXT_PLAIN)
                   .request(method, "/length/10")
                   .then()
                   .statusCode(200)
                   .contentType(ContentType.TEXT)
                   .header("content-length", "10")
                   .body(is("0000000000"));
        }
    }

    @Test
    public void length1024() {
        for (Method method : METHODS) {
            given().when()
                   .accept(MediaType.APPLICATION_OCTET_STREAM)
                   .request(method, "/length/1024")
                   .then()
                   .statusCode(200)
                   .contentType(ContentType.BINARY)
                   .header("content-length", "1024")
                   .body(notNullValue());
        }
    }

    @Test
    public void length2049() {
        for (Method method : METHODS) {
            given().when()
                   .accept(MediaType.APPLICATION_OCTET_STREAM)
                   .request(method, "/length/2049")
                   .then()
                   .statusCode(500)
                   .contentType(ContentType.TEXT)
                   .body(is("Invalid size: 2049"));
        }
    }

}