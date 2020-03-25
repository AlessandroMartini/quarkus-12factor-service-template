package com.v2com.iws10.axon.template.service;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@Liveness
@ApplicationScoped
public class PingResourceHealthCheck implements HealthCheck {

    @Inject
    Base base;

    @Override
    public HealthCheckResponse call() {
        // Verifica recursos ativos.
        // Conexão com o banco, redis, kafka, zookeeper, etc...
        base.Hello();
        // e devolve o status
        return HealthCheckResponse.named("Ping REST Endpoint").up().build();
    }
}
