package com.v2com.iws10.axon.template.service;

import com.v2com.iws10.axon.template.service.client.CountriesService;
import com.v2com.iws10.axon.template.service.model.Country;
import java.util.Set;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

@Path("/country")
public class CountriesResource {

  @Inject
  @RestClient
  CountriesService countriesService;

  @GET
  @Path("/name/{name}")
  @Produces(MediaType.APPLICATION_JSON)
  public Set<Country> name(@PathParam String name) {
    return countriesService.getByName(name);
  }
}
