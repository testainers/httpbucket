package com.testainers

import io.quarkus.test.junit.QuarkusTest
import io.restassured.http.Method
import org.hamcrest.Matchers.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.*

/**
 * @author Eduardo Folly
 */
@QuarkusTest
class DelayResourceTest : BaseResourceTest() {
    companion object : BaseResourceTest() {
        @JvmStatic
        fun invalidDelay(): List<Arguments> = argumentGenerator(listOf(-1, 11))

        @JvmStatic
        fun validDelay(): List<Arguments> = argumentGenerator(listOf(0, 1))
    }

    @ParameterizedTest
    @MethodSource("onlyMethods")
    fun delayEmpty(method: Method) {
        json(method)
            .request(method, "/delay")
            .then()
            .statusCode(404)
            .statusLine(containsStringIgnoringCase("Not Found"))
    }

    @ParameterizedTest
    @MethodSource("notFoundStatus")
    fun notFound(
        method: Method,
        delay: String?,
    ) {
        json(method)
            .request(method, "/delay/$delay")
            .then()
            .statusCode(404)
            .statusLine(containsStringIgnoringCase("Not Found"))
    }

    @ParameterizedTest
    @MethodSource("invalidDelay")
    fun invalid(
        method: Method,
        delay: Int,
    ) {
        json(method)
            .request(method, "/delay/$delay")
            .then()
            .statusCode(400)
            .apply {
                if (method != Method.HEAD) {
                    body(
                        "body",
                        equalTo("Invalid delay: $delay"),
                        *bodyMatchers(method),
                    )
                }
            }
    }

    @ParameterizedTest
    @MethodSource("validDelay")
    fun valid(
        method: Method,
        delay: Int,
    ) {
        json(method)
            .request(method, "/delay/$delay")
            .then()
            .statusCode(200)
            .apply {
                if (method != Method.HEAD) {
                    body(
                        "body",
                        equalTo("Slept for $delay seconds."),
                        *bodyMatchers(method),
                    )
                }
            }
    }
}
