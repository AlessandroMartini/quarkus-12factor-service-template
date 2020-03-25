package com.v2com.iws10.axon.template.service;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class baseTest {

    @Test
    public void testHelloEndpoint() {
        given()
          .when().get("/api/base")
          .then()
             .statusCode(200)
             .body(is("hello"));
    }

}