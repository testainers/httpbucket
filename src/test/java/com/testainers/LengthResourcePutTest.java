package com.testainers;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.ws.rs.core.MediaType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

/**
 * @author Eduardo Folly
 */
@QuarkusTest
public class LengthResourcePutTest {

    @Test
    public void testLengthPut0() {
        given().when()
               .put("/length/0")
               .then()
               .statusCode(500)
               .contentType(ContentType.TEXT)
               .body(is("Invalid size: 0"));
    }

    @Test
    public void testLengthPut5() {
        given().when()
               .put("/length/5")
               .then()
               .statusCode(200)
               .contentType(ContentType.TEXT)
               .header("content-length", "5")
               .body(is("00000"));
    }

    @Test
    public void testLengthPut10() {
        given().when()
               .accept(MediaType.TEXT_PLAIN)
               .put("/length/10")
               .then()
               .statusCode(200)
               .contentType(ContentType.TEXT)
               .header("content-length", "10")
               .body(is("0000000000"));
    }

    @Test
    public void testLengthPut1024() {
        given().when()
               .accept(MediaType.APPLICATION_OCTET_STREAM)
               .put("/length/1024")
               .then()
               .statusCode(200)
               .contentType(ContentType.BINARY)
               .header("content-length", "1024")
               .body(notNullValue());
    }

    @Test
    public void testLengthPut2049() {
        given().when()
               .accept(MediaType.APPLICATION_OCTET_STREAM)
               .put("/length/2049")
               .then()
               .statusCode(500)
               .contentType(ContentType.TEXT)
               .body(is("Invalid size: 2049"));
    }

}