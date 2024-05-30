package com.testainers

import io.quarkus.test.junit.QuarkusTest
import io.restassured.http.Method
import org.hamcrest.Matchers.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

/**
 * @author Eduardo Folly
 */
@QuarkusTest
class StatusResourceTest : BaseResourceTest() {
    companion object : BaseResourceTest() {
        @JvmStatic
        fun unknownStatus(): List<Arguments> =
            argumentGenerator(listOf(-1, 0, 1, 99, 600))

        @JvmStatic
        fun informationalStatus(): List<Arguments> =
            argumentGenerator(listOf(100, 199))

        @JvmStatic
        fun validStatus(): List<Arguments> = argumentGenerator(listOf(200, 599))

        @JvmStatic
        fun emptyStatus(): List<Arguments> =
            argumentGenerator(listOf(204, 205, 304))
    }

    @ParameterizedTest
    @MethodSource("notFoundStatus")
    fun notFound(
        method: Method,
        status: String?,
    ) {
        json(method)
            .request(method, "/status/$status")
            .then()
            .statusCode(404)
            .statusLine(containsStringIgnoringCase("Not Found"))
    }

    @ParameterizedTest
    @MethodSource("unknownStatus")
    fun unknown(
        method: Method,
        status: Int,
    ) {
        json(method)
            .request(method, "/status/$status")
            .then()
            .statusCode(500)
            .body(
                "body",
                equalTo("Unknown status code: $status"),
                *bodyMatchers(method),
            )
    }

    @ParameterizedTest
    @MethodSource("informationalStatus")
    fun informational(
        method: Method,
        status: Int,
    ) {
        json(method)
            .request(method, "/status/$status")
            .then()
            .statusCode(500)
            .body(
                "body",
                equalTo("Informational responses are not supported: $status"),
                *bodyMatchers(method),
            )
    }

    @ParameterizedTest
    @MethodSource("validStatus")
    fun valid(
        method: Method,
        status: Int,
    ) {
        json(method)
            .request(method, "/status/$status")
            .then()
            .statusCode(status)
            .body(
                "body",
                if (method == Method.GET) not(body) else equalTo(body),
                *bodyMatchers(method),
            )
    }

    @ParameterizedTest
    @MethodSource("emptyStatus")
    fun empty(
        method: Method,
        status: Int,
    ) {
        json(method)
            .request(method, "/status/$status")
            .then()
            .statusCode(status)
            .body(equalTo(""))
    }
}
