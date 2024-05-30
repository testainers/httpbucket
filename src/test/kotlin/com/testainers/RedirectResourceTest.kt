package com.testainers

import io.quarkus.test.junit.QuarkusTest
import io.restassured.http.ContentType
import io.restassured.http.Method
import io.restassured.module.kotlin.extensions.*
import jakarta.ws.rs.core.HttpHeaders
import org.hamcrest.Matchers.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

/**
 * @author Eduardo Folly
 */
@QuarkusTest
class RedirectResourceTest : BaseResourceTest() {
    companion object : BaseResourceTest() {
        const val LOCATION = "https://testainers.com"

        @JvmStatic
        fun invalidRedirect(): List<Arguments> =
            argumentGenerator(listOf(299, 400))

        @JvmStatic
        fun requiredUrl(): List<Arguments> =
            argumentGenerator(listOf(null, "", " "))

        @JvmStatic
        fun invalidUrl(): List<Arguments> =
            argumentGenerator(listOf("a{b}", "a:|b"))

        @JvmStatic
        fun invalidScheme(): List<Arguments> =
            argumentGenerator(
                listOf(
                    "testainers.com",
                    "1.8",
                    "a@b",
                    "ftp://teste.com",
                ),
            )

        @JvmStatic
        fun validRedirect(): List<Arguments> =
            argumentGenerator(listOf(null, "", "302", "303"))
    }

    @ParameterizedTest
    @MethodSource("invalidRedirect")
    fun invalid(
        it: Method,
        code: Int,
    ) {
        Given {
            config(config)
        } When {
            queryParam("url", LOCATION)
            queryParam("code", code)
            request(it, "/redirect")
        } Then {
            statusCode(500)
            contentType(ContentType.TEXT)
            body(equalTo("Invalid status code: $code"))
        }
    }

    @ParameterizedTest
    @MethodSource("validRedirect")
    fun valid(
        it: Method,
        code: String?,
    ) {
        Given {
            config(config)
        } When {
            queryParam("url", LOCATION)
            if (!code.isNullOrBlank()) {
                queryParam("code", code)
            }
            request(it, "/redirect")
        } Then {
            statusCode(code?.toIntOrNull() ?: 302)
            header(HttpHeaders.LOCATION, LOCATION)
            header(HttpHeaders.CONTENT_LENGTH, "0")
        }
    }

    @ParameterizedTest
    @MethodSource("requiredUrl")
    fun required(
        it: Method,
        url: String?,
    ) {
        Given {
            config(config)
        } When {
            if (url != null) {
                queryParam("url", url)
            }
            request(it, "/redirect")
        } Then {
            statusCode(500)
            contentType(ContentType.TEXT)
            body(equalTo("URL is required"))
        }
    }

    @ParameterizedTest
    @MethodSource("invalidUrl")
    fun invalid(
        it: Method,
        url: String?,
    ) {
        Given {
            config(config)
        } When {
            if (url != null) {
                queryParam("url", url)
            }
            request(it, "/redirect")
        } Then {
            statusCode(500)
            contentType(ContentType.TEXT)
            body(equalTo("Invalid URL: $url"))
        }
    }

    @ParameterizedTest
    @MethodSource("invalidScheme")
    fun scheme(
        it: Method,
        url: String?,
    ) {
        Given {
            config(config)
        } When {
            if (url != null) {
                queryParam("url", url)
            }
            request(it, "/redirect")
        } Then {
            statusCode(500)
            contentType(ContentType.TEXT)
            body(equalTo("Invalid URL Scheme: $url"))
        }
    }
}
