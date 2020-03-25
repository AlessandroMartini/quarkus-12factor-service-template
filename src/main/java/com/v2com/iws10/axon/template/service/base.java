package com.v2com.iws10.axon.template.service;

import com.v2com.iws10.axon.template.service.client.CountriesService;
import com.v2com.iws10.axon.template.service.client.Country;
import org.eclipse.microprofile.rest.client.inject.RestClient;
// Atenção no import do PathParam
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Set;

@Path("/api/base")
public class Base {

    @Inject
    @RestClient
    CountriesService countriesService;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/hello")
    public String Hello() {
        return "hello";
    }

    @GET
    @Path("/countries/{name}")
    @Produces(MediaType.TEXT_PLAIN)
    public String Countries(@PathParam String name) {
        return countriesService.getByName("Brazil").toString();
    }

}
