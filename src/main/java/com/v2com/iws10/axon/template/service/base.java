package com.v2com.iws10.axon.template.service;

import com.v2com.iws10.axon.template.service.client.CountriesService;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

// Atenção no import do PathParam

@Path("/api/base")
public class Base {

  @Inject
  @RestClient
  CountriesService countriesService;

  @GET
  @Produces(MediaType.TEXT_PLAIN)
  @Path("/hello")
  public String hello() {
    return "hello";
  }

  @GET
  @Path("/countries/{name}")
  @Produces(MediaType.TEXT_PLAIN)
  public String countries(@PathParam String name) {
    return countriesService.getByName("Brazil").toString();
  }

}
