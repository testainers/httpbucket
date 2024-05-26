package com.testainers

import jakarta.enterprise.context.ApplicationScoped
import org.eclipse.microprofile.health.*

/**
 * @author Eduardo Folly
 */
@Liveness
@ApplicationScoped
class SimpleHealthCheck : HealthCheck {
    override fun call(): HealthCheckResponse =
        HealthCheckResponse.up("httpbucket")
}