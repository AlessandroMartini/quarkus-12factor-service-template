package com.v2com.iws10.axon.template.service.zookeeper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import javax.inject.Inject;

import com.v2com.iws10.axon.template.service.model.HostConfig;
import io.quarkus.test.junit.QuarkusTest;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.x.async.AsyncCuratorFramework;
import org.apache.curator.x.async.modeled.JacksonModelSerializer;
import org.apache.curator.x.async.modeled.ModelSpec;
import org.apache.curator.x.async.modeled.ModeledFramework;
import org.apache.curator.x.async.modeled.ZPath;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class ModelTypedTest {

  @Inject
  CuratorFramework client;

  @Test
  public void givenPath_whenStoreAModel_thenNodesAreCreated()
      throws InterruptedException {

    ModelSpec<HostConfig> mySpec = ModelSpec
        .builder(ZPath.parseWithIds("/config/app"),
            JacksonModelSerializer.build(HostConfig.class))
        .build();

    try {
      client.start();
      AsyncCuratorFramework async = AsyncCuratorFramework.wrap(client);
      ModeledFramework<HostConfig> modeledClient = ModeledFramework
          .wrap(async, mySpec);

      modeledClient.set(new HostConfig("hostname.com", 8080));

      modeledClient.read()
          .whenComplete((value, e) -> {
            if (e != null) {
              fail("Cannot read host config", e);
            } else {
              assertThat(value).isNotNull();
              assertThat(value.getHostname()).isEqualTo("hostname.com");
              assertThat(value.getPort()).isEqualTo(8080);
            }

          });
    } finally {
      client.close();
    }

  }
}
