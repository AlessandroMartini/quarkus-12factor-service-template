package com.v2com.iws10.axon.template.service.zookeeper;

import static com.jayway.awaitility.Awaitility.await;
import static org.assertj.core.api.Assertions.assertThat;

import io.quarkus.test.junit.QuarkusTest;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.apache.curator.x.async.AsyncCuratorFramework;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class ConnectionManagementTest {

  @ConfigProperty(name = "zookeeper.address", defaultValue="localhost:2181")
  String zookeeperAddress;

  @Test
  public void givenRunningZookeeper_whenOpenConnection_thenClientIsOpened()
      throws Exception {
    int sleepMsBetweenRetries = 100;
    int maxRetries = 30;
    RetryPolicy retryPolicy = new RetryNTimes(maxRetries,
        sleepMsBetweenRetries);

    try (CuratorFramework client = CuratorFrameworkFactory
        .newClient(zookeeperAddress, retryPolicy)) {
      client.start();

      assertThat(client.checkExists()
          .forPath("/")).isNotNull();
    }
  }

  @Test
  public void givenRunningZookeeper_whenOpenConnectionUsingAsyncNotBlocking_thenClientIsOpened()
      throws InterruptedException {
    int sleepMsBetweenRetries = 100;
    int maxRetries = 3;
    RetryPolicy retryPolicy = new RetryNTimes(maxRetries,
        sleepMsBetweenRetries);

    try (CuratorFramework client = CuratorFrameworkFactory
        .newClient(zookeeperAddress, retryPolicy)) {
      client.start();
      AsyncCuratorFramework async = AsyncCuratorFramework.wrap(client);

      AtomicBoolean exists = new AtomicBoolean(false);

      async.checkExists()
          .forPath("/")
          .thenAcceptAsync(s -> exists.set(s != null));

      await().until(() -> assertThat(exists.get()).isTrue());
    }
  }

  @Test
  public void givenRunningZookeeper_whenOpenConnectionUsingAsyncBlocking_thenClientIsOpened()
      throws InterruptedException {
    int sleepMsBetweenRetries = 100;
    int maxRetries = 3;
    RetryPolicy retryPolicy = new RetryNTimes(maxRetries,
        sleepMsBetweenRetries);

    try (CuratorFramework client = CuratorFrameworkFactory
        .newClient(zookeeperAddress, retryPolicy)) {
      client.start();
      AsyncCuratorFramework async = AsyncCuratorFramework.wrap(client);

      AtomicBoolean exists = new AtomicBoolean(false);

      async.checkExists()
          .forPath("/")
          .thenAccept(s -> exists.set(s != null));

      await().until(() -> assertThat(exists.get()).isTrue());
    }
  }
}
