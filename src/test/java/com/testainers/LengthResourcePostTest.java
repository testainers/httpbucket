package com.testainers;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.ws.rs.core.MediaType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

/**
 * @author Eduardo Folly
 */
@QuarkusTest
public class LengthResourcePostTest {

    @Test
    public void testLengthPost0() {
        given().when()
               .post("/length/0")
               .then()
               .statusCode(500)
               .contentType(ContentType.TEXT)
               .body(is("Invalid size: 0"));
    }

    @Test
    public void testLengthPost5() {
        given().when()
               .post("/length/5")
               .then()
               .statusCode(200)
               .contentType(ContentType.TEXT)
               .header("content-length", "5")
               .body(is("00000"));
    }

    @Test
    public void testLengthPost10() {
        given().when()
               .accept(MediaType.TEXT_PLAIN)
               .post("/length/10")
               .then()
               .statusCode(200)
               .contentType(ContentType.TEXT)
               .header("content-length", "10")
               .body(is("0000000000"));
    }

    @Test
    public void testLengthPost1024() {
        given().when()
               .accept(MediaType.APPLICATION_OCTET_STREAM)
               .post("/length/1024")
               .then()
               .statusCode(200)
               .contentType(ContentType.BINARY)
               .header("content-length", "1024")
               .body(notNullValue());
    }

    @Test
    public void testLengthPost2049() {
        given().when()
               .accept(MediaType.APPLICATION_OCTET_STREAM)
               .post("/length/2049")
               .then()
               .statusCode(500)
               .contentType(ContentType.TEXT)
               .body(is("Invalid size: 2049"));
    }

}