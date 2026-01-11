package com.testainers

import io.quarkus.test.junit.QuarkusTest
import io.restassured.http.Method
import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.nullValue
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

/**
 * @author Eduardo Folly
 */
@QuarkusTest
class BasicAuthResourceTest : BaseResourceTest() {
    val user = "test"
    val pass = "test-pass0"

    @ParameterizedTest
    @MethodSource("onlyMethods")
    fun noHeader(method: Method) {
        json(method)
            .request(method, "/basic-auth/$user/$pass")
            .then()
            .statusCode(401)
            .apply {
                if (method != Method.HEAD) {
                    body(
                        "body.body",
                        if (method == Method.GET) {
                            nullValue()
                        } else {
                            equalTo(body)
                        },
                        *bodyMatchers(
                            method,
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
    }

    @ParameterizedTest
    @MethodSource("onlyMethods")
    fun success(method: Method) {
        json(method)
            .auth()
            .preemptive()
            .basic(user, pass)
            .request(method, "/basic-auth/$user/$pass")
            .then()
            .statusCode(200)
            .apply {
                if (method != Method.HEAD) {
                    body(
                        "body.body",
                        if (method == Method.GET) {
                            nullValue()
                        } else {
                            equalTo(body)
                        },
                        *bodyMatchers(
                            method,
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

    @ParameterizedTest
    @MethodSource("onlyMethods")
    fun fail(method: Method) {
        json(method)
            .auth()
            .preemptive()
            .basic(user + "A", pass + "A")
            .request(method, "/basic-auth/$user/$pass")
            .then()
            .statusCode(403)
            .apply {
                if (method != Method.HEAD) {
                    body(
                        "body.body",
                        if (method == Method.GET) {
                            nullValue()
                        } else {
                            equalTo(body)
                        },
                        *bodyMatchers(
                            method,
                            mapOf(
                                "body.auth" to equalTo(false),
                                "body.user" to equalTo(user),
                                "body.pass" to equalTo(pass),
                                "body.message" to equalTo("Forbidden."),
                            ),
                        ),
                    )
                }
            }
    }
}
