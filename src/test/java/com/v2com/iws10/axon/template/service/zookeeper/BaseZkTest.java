package com.v2com.iws10.axon.template.service.zookeeper;

import io.quarkus.test.junit.QuarkusTest;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.junit.Before;

@QuarkusTest
public abstract class BaseZkTest {

  @ConfigProperty(name = "zookeeper.address", defaultValue="localhost:2181")
  String zookeeperAddress;

  @Before
  public void setup() {
  }

  protected CuratorFramework newClient() {
    int sleepMsBetweenRetries = 100;
    int maxRetries = 30;
    RetryPolicy retryPolicy = new RetryNTimes(maxRetries, sleepMsBetweenRetries);
    return CuratorFrameworkFactory.newClient(zookeeperAddress, retryPolicy);
  }

}
