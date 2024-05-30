package com.testainers

import io.quarkus.test.junit.QuarkusTest
import io.restassured.http.Method
import org.hamcrest.Matchers.*
import org.junit.jupiter.api.Test

/**
 * @author Eduardo Folly
 */
@QuarkusTest
class MethodsResourceTest : BaseResourceTest() {
    @Test
    fun methodGet() {
        val method = Method.GET
        json(method)
            .request(method, "/methods")
            .then()
            .statusCode(200)
            .body("", notNullValue(), *bodyMatchers(method))
    }

    @Test
    fun methodHead() {
        json(Method.HEAD)
            .head("/methods")
            .then()
            .statusCode(200)
    }

    @Test
    fun methodPost() {
        val method = Method.POST
        json(method)
            .request(method, "/methods")
            .then()
            .statusCode(200)
            .body("body", equalTo(body), *bodyMatchers(method))
    }

    @Test
    fun methodPut() {
        val method = Method.PUT
        json(method)
            .request(method, "/methods")
            .then()
            .statusCode(200)
            .body("body", equalTo(body), *bodyMatchers(method))
    }

    @Test
    fun methodPatch() {
        val method = Method.PATCH
        json(method)
            .request(method, "/methods")
            .then()
            .statusCode(200)
            .body("body", equalTo(body), *bodyMatchers(method))
    }

    @Test
    fun methodDelete() {
        val method = Method.DELETE
        json(method)
            .request(method, "/methods")
            .then()
            .statusCode(200)
            .body("body", equalTo(body), *bodyMatchers(method))
    }
}
