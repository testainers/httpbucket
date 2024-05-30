package com.testainers

import io.quarkus.test.junit.QuarkusTest
import io.restassured.http.Method
import org.hamcrest.Matchers.*
import org.junit.jupiter.api.Test

/**
 * @author Eduardo Folly
 */
@QuarkusTest
class StatusResourceTest : BaseResourceTest() {
    @Test
    fun statusString() {
        methods.forEach {
            json(it)
                .request(it, "/status/a")
                .then()
                .statusCode(404)
                .statusLine(containsStringIgnoringCase("Not Found"))
        }
    }

    @Test
    fun statusDouble() {
        methods.forEach {
            json(it)
                .request(it, "/status/1.8")
                .then()
                .statusCode(404)
                .statusLine(containsStringIgnoringCase("Not Found"))
        }
    }

    @Test
    fun statusNegative() {
        methods.forEach {
            json(it)
                .request(it, "/status/-1")
                .then()
                .statusCode(500)
                .body(
                    "body",
                    equalTo("Unknown status code: -1"),
                    *bodyMatchers(it),
                )
        }
    }

    @Test
    fun status0() {
        methods.forEach {
            json(it)
                .request(it, "/status/0")
                .then()
                .statusCode(500)
                .body(
                    "body",
                    equalTo("Unknown status code: 0"),
                    *bodyMatchers(it),
                )
        }
    }

    @Test
    fun status99() {
        methods.forEach {
            json(it)
                .request(it, "/status/99")
                .then()
                .statusCode(500)
                .body(
                    "body",
                    equalTo("Unknown status code: 99"),
                    *bodyMatchers(it),
                )
        }
    }

    @Test
    fun status100() {
        methods.forEach {
            json(it)
                .request(it, "/status/100")
                .then()
                .statusCode(500)
                .body(
                    "body",
                    equalTo("Informational responses are not supported: 100"),
                    *bodyMatchers(it),
                )
        }
    }

    @Test
    fun status199() {
        methods.forEach {
            json(it)
                .request(it, "/status/199")
                .then()
                .statusCode(500)
                .body(
                    "body",
                    equalTo("Informational responses are not supported: 199"),
                    *bodyMatchers(it),
                )
        }
    }

    @Test
    fun status200() {
        methods.forEach {
            json(it)
                .request(it, "/status/200")
                .then()
                .statusCode(200)
                .body(
                    "body",
                    if (it == Method.GET) not(body) else equalTo(body),
                    *bodyMatchers(it),
                )
        }
    }

    @Test
    fun status204() {
        methods.forEach {
            json(it)
                .request(it, "/status/204")
                .then()
                .statusCode(204)
                .body(equalTo(""))
        }
    }

    @Test
    fun status205() {
        methods.forEach {
            json(it)
                .request(it, "/status/205")
                .then()
                .statusCode(205)
                .headers("content-length", "0")
                .body(equalTo(""))
        }
    }

    @Test
    fun status304() {
        methods.forEach {
            json(it)
                .request(it, "/status/304")
                .then()
                .statusCode(304)
                .body(equalTo(""))
        }
    }

    @Test
    fun status599() {
        methods.forEach {
            json(it)
                .request(it, "/status/599")
                .then()
                .statusCode(599)
                .body(
                    "body",
                    if (it == Method.GET) not(body) else equalTo(body),
                    *bodyMatchers(it),
                )
        }
    }

    @Test
    fun status600() {
        methods.forEach {
            json(it)
                .request(it, "/status/600")
                .then()
                .statusCode(500)
                .body(
                    "body",
                    equalTo("Unknown status code: 600"),
                    *bodyMatchers(it),
                )
        }
    }
}
