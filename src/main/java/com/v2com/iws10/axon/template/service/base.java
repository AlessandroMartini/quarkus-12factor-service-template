package com.v2com.iws10.axon.template.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/api/base")
public class Base {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String Hello() {
        return "hello";
    }
}
