package com.testainers

import io.quarkus.test.junit.QuarkusTest
import io.restassured.http.Method
import jakarta.ws.rs.core.HttpHeaders
import org.hamcrest.Matchers.*
import org.junit.jupiter.api.Test

/**
 * @author Eduardo Folly
 */
@QuarkusTest
class BasicAuthResourceTest : BaseResourceTest() {
    val user = "test"
    val pass = "test-pass0"

    @Test
    fun noHeader() {
        methods.forEach {
            json(it)
                .request(it, "/basic-auth/$user/$pass")
                .then()
                .statusCode(401)
                .body(
                    "body.body",
                    if (it == Method.GET) nullValue() else equalTo(body),
                    *bodyMatchers(
                        it,
                        mapOf(
                            "body.auth" to equalTo(false),
                            "body.user" to equalTo(user),
                            "body.pass" to equalTo(pass),
                            "body.message" to
                                equalTo(
                                    "Authorization header not present.",
                                ),
                        ),
                    ),
                )
        }
    }

    @Test
    fun emptyHeader() {
        methods.forEach {
            json(it)
                .header(HttpHeaders.AUTHORIZATION, "")
                .request(it, "/basic-auth/$user/$pass")
                .then()
                .statusCode(401)
                .body(
                    "body.body",
                    if (it == Method.GET) nullValue() else equalTo(body),
                    *bodyMatchers(
                        it,
                        mapOf(
                            "body.auth" to equalTo(false),
                            "body.user" to equalTo(user),
                            "body.pass" to equalTo(pass),
                            "body.message" to
                                equalTo(
                                    "Authorization header not present.",
                                ),
                        ),
                    ),
                )
        }
    }

    @Test
    fun success() {
        methods.forEach {
            json(it)
                .auth()
                .preemptive()
                .basic(user, pass)
                .request(it, "/basic-auth/$user/$pass")
                .then()
                .statusCode(200)
                .body(
                    "body.body",
                    if (it == Method.GET) nullValue() else equalTo(body),
                    *bodyMatchers(
                        it,
                        mapOf(
                            "body.auth" to equalTo(true),
                            "body.user" to equalTo(user),
                            "body.pass" to equalTo(pass),
                            "body.message" to equalTo("Success."),
                        ),
                    ),
                )
        }
    }
}
