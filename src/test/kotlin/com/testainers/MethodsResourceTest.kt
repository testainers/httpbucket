package com.testainers

import io.quarkus.test.junit.QuarkusTest
import io.restassured.http.Method
import org.hamcrest.Matchers.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

/**
 * @author Eduardo Folly
 */
@QuarkusTest
class MethodsResourceTest : BaseResourceTest() {
    @ParameterizedTest
    @MethodSource("onlyMethods")
    fun methodGet(method: Method) {
        json(method)
            .request(method, "/methods")
            .then()
            .statusCode(200)
            .apply {
                if (method != Method.HEAD) {
                    body(
                        "body",
                        if (method == Method.GET) {
                            nullValue()
                        } else {
                            equalTo(body)
                        },
                        *bodyMatchers(method),
                    )
                }
            }
    }
}
