package com.testainers

import io.quarkus.test.junit.QuarkusTest
import io.restassured.http.ContentType
import io.restassured.http.Method
import io.restassured.module.kotlin.extensions.*
import jakarta.ws.rs.core.MediaType
import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.notNullValue
import org.junit.jupiter.api.Test

/**
 * @author Eduardo Folly
 */
@QuarkusTest
class LengthResourceTest {
    val methods = listOf(Method.GET, Method.POST, Method.PUT, Method.DELETE)

    @Test
    fun length0() {
        methods.forEach {
            When {
                request(it, "/length/0")
            } Then {
                statusCode(500)
                contentType(ContentType.TEXT)
                body(equalTo("Invalid size: 0"))
            }
        }
    }

    @Test
    fun length5() {
        methods.forEach {
            When {
                request(it, "/length/5")
            } Then {
                statusCode(200)
                contentType(ContentType.TEXT)
                header("Content-Length", "5")
                body(equalTo("0".repeat(5)))
            }
        }
    }

    @Test
    fun length10() {
        methods.forEach {
            When {
                request(it, "/length/10")
            } Then {
                statusCode(200)
                contentType(ContentType.TEXT)
                header("Content-Length", "10")
                body(equalTo("0".repeat(10)))
            }
        }
    }

    @Test
    fun length1024() {
        methods.forEach {
            Given {
                given()
                accept(MediaType.APPLICATION_OCTET_STREAM)
            } When {
                request(it, "/length/1024")
            } Then {
                statusCode(200)
                contentType(ContentType.BINARY)
                header("Content-Length", "1024")
                body(notNullValue())
            }
        }
    }

    @Test
    fun length2049() {
        methods.forEach {
            Given {
                given()
                accept(MediaType.APPLICATION_OCTET_STREAM)
            } When {
                request(it, "/length/2049")
            } Then {
                statusCode(500)
                contentType(ContentType.TEXT)
                body(equalTo("Invalid size: 2049"))
            }
        }
    }
}
