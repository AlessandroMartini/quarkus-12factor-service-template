package com.v2com.iws10.axon.template.service.soap;

import javax.enterprise.event.Observes;
import javax.xml.ws.Endpoint;

import com.v2com.iws10.axon.template.service.soap.client.EmployeeServiceTopDown;
import com.v2com.iws10.axon.template.service.soap.client.EmployeeServiceTopDownImpService;
import com.v2com.iws10.axon.template.service.soap.server.EmployeeServiceTopDownImp;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.quarkus.runtime.StartupEvent;

public class EndpointInitializer {
  static private final Logger logger = LoggerFactory.getLogger(EndpointInitializer.class);

  @ConfigProperty(name = "com.v2com.iws10.axon.employee.url")
  String urlSoap;

  public void init(@Observes StartupEvent ev) {
    logger.info("Publishing endpoint: {}", urlSoap);
    Endpoint.publish(urlSoap, new EmployeeServiceTopDownImp());

    //Consuming endpoint 
    EmployeeServiceTopDownImpService service = new EmployeeServiceTopDownImpService();
    EmployeeServiceTopDown employeeServiceTopDownPort = service.getEmployeeServiceTopDownPort();

    Integer employees = employeeServiceTopDownPort.countEmployees();
    logger.info("Consuming endpoint...");
    logger.info("[employees]: {}", employees, employees);

  }

}