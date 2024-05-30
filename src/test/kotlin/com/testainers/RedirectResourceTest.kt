package com.testainers

import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured
import io.restassured.config.LogConfig
import io.restassured.config.RedirectConfig
import io.restassured.http.ContentType
import io.restassured.http.Method
import io.restassured.module.kotlin.extensions.*
import jakarta.ws.rs.core.HttpHeaders
import org.hamcrest.Matchers.*
import org.junit.jupiter.api.Test

/**
 * @author Eduardo Folly
 */
@QuarkusTest
class RedirectResourceTest {
    val location = "https://testainers.com"

    val invalidSchema = "testainers.com"

    val methods =
        listOf(Method.GET, Method.POST, Method.PUT, Method.PATCH, Method.DELETE)

    val config =
        RestAssured
            .config()
            .logConfig(
                LogConfig
                    .logConfig()
                    .enableLoggingOfRequestAndResponseIfValidationFails()
                    .enablePrettyPrinting(true),
            ).redirect(
                RedirectConfig.redirectConfig().followRedirects(false),
            )

    @Test
    fun redirect302() {
        methods.forEach {
            Given {
                config(config)
            } When {
                queryParam("url", location)
                request(it, "/redirect")
            } Then {
                statusCode(302)
                header(HttpHeaders.LOCATION, location)
                header(HttpHeaders.CONTENT_LENGTH, "0")
            }
        }
    }

    @Test
    fun redirect303() {
        methods.forEach {
            Given {
                config(config)
            } When {
                queryParam("url", location)
                queryParam("code", 303)
                request(it, "/redirect")
            } Then {
                statusCode(303)
                header(HttpHeaders.LOCATION, location)
                header(HttpHeaders.CONTENT_LENGTH, "0")
            }
        }
    }

    @Test
    fun redirect299() {
        methods.forEach {
            Given {
                config(config)
            } When {
                queryParam("url", location)
                queryParam("code", 299)
                request(it, "/redirect")
            } Then {
                statusCode(500)
                contentType(ContentType.TEXT)
                body(equalTo("Invalid status code: 299"))
            }
        }
    }

    @Test
    fun redirect499() {
        methods.forEach {
            Given {
                config(config)
            } When {
                queryParam("url", location)
                queryParam("code", 499)
                request(it, "/redirect")
            } Then {
                statusCode(500)
                contentType(ContentType.TEXT)
                body(equalTo("Invalid status code: 499"))
            }
        }
    }

    @Test
    fun redirectNoUrl() {
        methods.forEach {
            Given {
                config(config)
            } When {
                request(it, "/redirect")
            } Then {
                statusCode(500)
                contentType(ContentType.TEXT)
                body(equalTo("Invalid URL Scheme: "))
            }
        }
    }

    @Test
    fun redirectEmptyUrl() {
        methods.forEach {
            Given {
                config(config)
            } When {
                queryParam("url", "")
                request(it, "/redirect")
            } Then {
                statusCode(500)
                contentType(ContentType.TEXT)
                body(equalTo("Invalid URL Scheme: "))
            }
        }
    }

    @Test
    fun redirectInvalidScheme() {
        methods.forEach {
            Given {
                config(config)
            } When {
                queryParam("url", invalidSchema)
                request(it, "/redirect")
            } Then {
                statusCode(500)
                contentType(ContentType.TEXT)
                body(equalTo("Invalid URL Scheme: $invalidSchema"))
            }
        }
    }
}
