package com.v2com.iws10.axon.template.service.zookeeper;

import javax.enterprise.inject.Produces;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.eclipse.microprofile.config.inject.ConfigProperty;

public class CuratorClientFactory {

  @ConfigProperty(name = "zookeeper.address", defaultValue="localhost:2181")
  String zookeeperAddress;

  private int sleepMsBetweenRetries = 100;
  private int maxRetries = 30;

  @Produces
  public CuratorFramework newClient() {
    RetryPolicy retryPolicy = new RetryNTimes(maxRetries, sleepMsBetweenRetries);
    CuratorFramework client = CuratorFrameworkFactory.newClient(zookeeperAddress, retryPolicy);
    return client;
  }

  public CuratorFramework newStartedClient() {
    CuratorFramework client = newClient();
    client.start();

    return client;
  }
}
