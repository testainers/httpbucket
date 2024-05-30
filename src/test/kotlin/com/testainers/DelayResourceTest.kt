package com.testainers

import io.quarkus.test.junit.QuarkusTest
import org.hamcrest.Matchers.*
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

/**
 * @author Eduardo Folly
 */
@QuarkusTest
class DelayResourceTest : BaseResourceTest() {
    @Test
    fun delayEmpty() {
        methods.forEach {
            json(it)
                .request(it, "/delay")
                .then()
                .statusCode(404)
                .statusLine(containsStringIgnoringCase("Not Found"))
        }
    }

    @Test
    fun delayString() {
        methods.forEach {
            json(it)
                .request(it, "/delay/a")
                .then()
                .statusCode(404)
                .statusLine(containsStringIgnoringCase("Not Found"))
        }
    }

    @Test
    fun delayDouble() {
        methods.forEach {
            json(it)
                .request(it, "/delay/1.8")
                .then()
                .statusCode(404)
                .statusLine(containsStringIgnoringCase("Not Found"))
        }
    }

    @Test
    fun delayNegative() {
        methods.forEach {
            json(it)
                .request(it, "/delay/-1")
                .then()
                .statusCode(400)
                .body("body", equalTo("Invalid delay: -1"), *bodyMatchers(it))
        }
    }

    @Test
    fun delay0() {
        methods.forEach {
            json(it)
                .request(it, "/delay/0")
                .then()
                .statusCode(200)
                .body(
                    "body",
                    equalTo("Slept for 0 seconds."),
                    *bodyMatchers(it),
                )
        }
    }

    @Test
    fun delay1() {
        methods.forEach {
            json(it)
                .request(it, "/delay/1")
                .then()
                .statusCode(200)
                .body(
                    "body",
                    equalTo("Slept for 1 seconds."),
                    *bodyMatchers(it),
                )
        }
    }

    @Test
    @Disabled
    fun delay10() {
        methods.forEach {
            json(it)
                .request(it, "/delay/10")
                .then()
                .statusCode(200)
                .body(
                    "body",
                    equalTo("Slept for 10 seconds."),
                    *bodyMatchers(it),
                )
        }
    }

    @Test
    fun delay11() {
        methods.forEach {
            json(it)
                .request(it, "/delay/11")
                .then()
                .statusCode(400)
                .body(
                    "body",
                    equalTo("Invalid delay: 11"),
                    *bodyMatchers(it),
                )
        }
    }
}
