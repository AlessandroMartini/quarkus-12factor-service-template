package com.v2com.iws10.axon.template.service.client;

import com.v2com.iws10.axon.template.service.model.Country;
import java.util.Set;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

@Path("/v2")
@RegisterRestClient(configKey = "country-api")
public interface CountriesService {

  @GET
  @Path("/name/{name}")
  @Produces("application/json")
  Set<Country> getByName(@PathParam String name);
}
