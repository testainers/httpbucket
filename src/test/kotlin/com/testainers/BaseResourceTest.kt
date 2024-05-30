package com.testainers

import io.restassured.RestAssured
import io.restassured.config.*
import io.restassured.http.ContentType
import io.restassured.http.Method
import io.restassured.specification.RequestSpecification
import org.hamcrest.Matcher
import org.hamcrest.Matchers.*
import org.junit.jupiter.params.provider.Arguments

/**
 * @author Eduardo Folly
 */
abstract class BaseResourceTest {
    companion object {
        private val methods =
            listOf(Method.GET, Method.POST, Method.PUT, Method.DELETE)

        @JvmStatic
        fun notFoundStatus(): List<Arguments> =
            argumentGenerator(listOf(null, "", " ", "a", "1.8"))

        @JvmStatic
        fun onlyMethods(): List<Arguments> = methods.map { Arguments.of(it) }

        @JvmStatic
        fun argumentGenerator(list: List<Any?>): List<Arguments> {
            val result = mutableListOf<Arguments>()

            methods.forEach { method ->
                list.forEach { status ->
                    result.add(Arguments.of(method, status))
                }
            }

            return result
        }
    }

//    @Deprecated("Not use")
//    protected val methods =
//        listOf(Method.GET, Method.POST, Method.PUT, Method.DELETE)

    protected val config: RestAssuredConfig =
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

    private val headers =
        mapOf(
            "test-header" to listOf("test-header-value"),
            "test-header-2" to listOf("test-header-value-2"),
        )

    private val queryParams = mapOf("test-qs" to listOf("test-qs-value"))

    protected val body =
        mapOf(
            "test-string" to "test",
            "test-int" to 1,
            "test-boolean" to true,
        )

    fun json(method: Method): RequestSpecification {
        val spec =
            RestAssured
                .given()
                .config(config)
                .`when`()
                .headers(headers)
                .queryParams(queryParams)
                .contentType(ContentType.JSON)

        if (method != Method.GET) {
            spec.body(body)
        }

        return spec
    }

    fun bodyMatchers(
        method: Method,
        args: Map<String, Matcher<*>> = emptyMap(),
    ): Array<Any?> {
        val matchers = mutableListOf<Any?>()

        matchers.add("method")
        matchers.add(equalTo(method.name))

        matchers.add("queryParameters")
        matchers.add(equalTo(queryParams))

        matchers.add("headers")
        matchers.add(
            aMapWithSize<String, Any?>(greaterThanOrEqualTo(headers.size)),
        )

        matchers.add("headers")
        matchers.add(hasEntry(`in`(headers.keys), `in`(headers.values)))

        if (args.isNotEmpty()) {
            args.forEach { (key, value) ->
                matchers.add(key)
                matchers.add(value)
            }
        }

        return matchers.toTypedArray()
    }
}
