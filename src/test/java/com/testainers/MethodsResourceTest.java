package com.testainers;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.Method;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;

/**
 * @author Eduardo Folly
 */
@QuarkusTest
public class MethodsResourceTest extends BaseResourceTest {

    @Test
    public void methodsGet() {
        base()
                .get("/methods")
                .then()
                .statusCode(200)
                .body("", not(""), bodyMatchers(Method.GET));
    }

    @Test
    public void methodsHead() {
        base().head("/methods")
              .then()
              .statusCode(200);
    }

    @Test
    public void methodsPost() {
        json(Method.POST)
                .post("/methods")
                .then()
                .statusCode(200)
                .body("body", is(BODY), bodyMatchers(Method.POST));
    }

    @Test
    public void methodsPut() {
        json(Method.PUT)
                .put("/methods")
                .then()
                .statusCode(200)
                .body("body", is(BODY), bodyMatchers(Method.PUT));
    }

    @Test
    public void methodsPatch() {
        json(Method.PATCH)
                .patch("/methods")
                .then()
                .statusCode(200)
                .body("body", is(BODY), bodyMatchers(Method.PATCH));
    }

    @Test
    public void methodsDelete() {
        json(Method.DELETE)
                .delete("/methods")
                .then()
                .statusCode(200)
                .body("body", is(BODY), bodyMatchers(Method.DELETE));
    }

}