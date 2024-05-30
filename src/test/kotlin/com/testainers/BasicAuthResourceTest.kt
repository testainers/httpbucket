package com.testainers

import io.quarkus.test.junit.QuarkusTest
import io.restassured.http.Method
import jakarta.ws.rs.core.HttpHeaders
import org.hamcrest.Matchers.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

/**
 * @author Eduardo Folly
 */
@QuarkusTest
class BasicAuthResourceTest : BaseResourceTest() {
    companion object : BaseResourceTest() {
        const val USER = "test"
        const val PASS = "test-pass0"
    }

    @ParameterizedTest
    @MethodSource("onlyMethods")
    fun noHeader(method: Method) {
        json(method)
            .request(method, "/basic-auth/$USER/$PASS")
            .then()
            .statusCode(401)
            .body(
                "body.body",
                if (method == Method.GET) nullValue() else equalTo(body),
                *bodyMatchers(
                    method,
                    mapOf(
                        "body.auth" to equalTo(false),
                        "body.user" to equalTo(USER),
                        "body.pass" to equalTo(PASS),
                        "body.message" to
                            equalTo(
                                "Authorization header not present.",
                            ),
                    ),
                ),
            )
    }

    @ParameterizedTest
    @MethodSource("onlyMethods")
    fun emptyHeader(method: Method) {
        json(method)
            .header(HttpHeaders.AUTHORIZATION, "")
            .request(method, "/basic-auth/$USER/$PASS")
            .then()
            .statusCode(401)
            .body(
                "body.body",
                if (method == Method.GET) nullValue() else equalTo(body),
                *bodyMatchers(
                    method,
                    mapOf(
                        "body.auth" to equalTo(false),
                        "body.user" to equalTo(USER),
                        "body.pass" to equalTo(PASS),
                        "body.message" to
                            equalTo(
                                "Authorization header not present.",
                            ),
                    ),
                ),
            )
    }

    @ParameterizedTest
    @MethodSource("onlyMethods")
    fun success(method: Method) {
        json(method)
            .auth()
            .preemptive()
            .basic(USER, PASS)
            .request(method, "/basic-auth/$USER/$PASS")
            .then()
            .statusCode(200)
            .body(
                "body.body",
                if (method == Method.GET) nullValue() else equalTo(body),
                *bodyMatchers(
                    method,
                    mapOf(
                        "body.auth" to equalTo(true),
                        "body.user" to equalTo(USER),
                        "body.pass" to equalTo(PASS),
                        "body.message" to equalTo("Success."),
                    ),
                ),
            )
    }
}
