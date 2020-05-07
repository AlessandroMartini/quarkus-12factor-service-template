package com.v2com.iws10.axon.template.service;

import static io.restassured.RestAssured.given;
import static javax.ws.rs.core.Response.Status.OK;
import static org.hamcrest.CoreMatchers.is;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class BaseTest {

  @Test
  public void testHelloEndpoint() {
    given()
        .when().get("/api/base/hello")
        .then()
        .statusCode(200)
        .body(is("hello"));
  }

  @Test
  void shouldPingLiveness() {
    given()
        .when().get("/health/live")
        .then()
        .statusCode(OK.getStatusCode());
  }

  @Test
  void shouldPingReadiness() {
    given()
        .when().get("/health/ready")
        .then()
        .statusCode(OK.getStatusCode());
  }

}
