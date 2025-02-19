package com.testainers

import io.quarkus.test.junit.QuarkusTest
import io.restassured.http.ContentType
import io.restassured.http.Method
import io.restassured.module.kotlin.extensions.*
import jakarta.ws.rs.core.MediaType
import org.hamcrest.Matchers.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

/**
 * @author Eduardo Folly
 */
@QuarkusTest
class LengthResourceTest : BaseResourceTest() {
    companion object : BaseResourceTest() {
        @JvmStatic
        fun invalidLength(): List<Arguments> =
            argumentGenerator(listOf(-1, 0, 2049), remove = listOf(Method.HEAD))

        @JvmStatic
        fun successTextLength(): List<Arguments> =
            argumentGenerator(listOf(1, 10), remove = listOf(Method.HEAD))

        @JvmStatic
        fun successOctetLength(): List<Arguments> =
            argumentGenerator(listOf(512, 2048), remove = listOf(Method.HEAD))
    }

    @ParameterizedTest
    @MethodSource("notFoundStatus")
    fun notFound(
        method: Method,
        length: String?,
    ) {
        json(method)
            .request(method, "/length/$length")
            .then()
            .statusCode(404)
            .statusLine(containsStringIgnoringCase("Not Found"))
    }

    @ParameterizedTest
    @MethodSource("invalidLength")
    fun invalid(
        method: Method,
        length: Int,
    ) {
        When {
            request(method, "/length/$length")
        } Then {
            statusCode(500)
            contentType(ContentType.TEXT)
            body(equalTo("Invalid size: $length"))
        }
    }

    @ParameterizedTest
    @MethodSource("successTextLength")
    fun successText(
        it: Method,
        length: Int,
    ) {
        When {
            request(it, "/length/$length")
        } Then {
            statusCode(200)
            contentType(ContentType.TEXT)
            header("Content-Length", "$length")
            body(equalTo("0".repeat(length)))
        }
    }

    @ParameterizedTest
    @MethodSource("successOctetLength")
    fun successOctet(
        it: Method,
        length: Int,
    ) {
        Given {
            given()
            accept(MediaType.APPLICATION_OCTET_STREAM)
        } When {
            request(it, "/length/$length")
        } Then {
            statusCode(200)
            contentType(ContentType.BINARY)
            header("Content-Length", "$length")
            body(notNullValue())
        }
    }
}
