package com.v2com.iws10.axon.template.service;


import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.HealthCheckResponseBuilder;
import org.eclipse.microprofile.health.Readiness;

import javax.enterprise.context.ApplicationScoped;

@Readiness
@ApplicationScoped
public class DatabaseConnectionHealthCheck implements HealthCheck {

    @Override
    public HealthCheckResponse call() {
        HealthCheckResponseBuilder responseBuilder = HealthCheckResponse
                .named("Bases Datasource connection health check");
        try {
            Integer qtd = 0;
            // get indicators from a dependency/resource
            // List<Bases> bases = baseService.findAllBases();
            // Integer qtd = bases.size()).up();
            responseBuilder.withData("Number of bases in the database", qtd).up();
        } catch (IllegalStateException e) {
            responseBuilder.down();
        }
        return responseBuilder.build();
    }
}
