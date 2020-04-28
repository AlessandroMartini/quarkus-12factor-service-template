package com.v2com.iws10.axon.template.service;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;

@Liveness
@ApplicationScoped
public class PingResourceHealthCheck implements HealthCheck {

  @Inject
  Base base;

  @Override
  public HealthCheckResponse call() {
    // Verifica recursos ativos.
    // Conexão com o banco, redis, kafka, zookeeper, etc...
    base.hello();
    // e devolve o status
    return HealthCheckResponse.named("Ping REST Endpoint").up().build();
  }
}
