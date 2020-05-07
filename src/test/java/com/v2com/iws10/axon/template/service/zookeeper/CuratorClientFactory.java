package com.v2com.iws10.axon.template.service.zookeeper;

import javax.enterprise.inject.Produces;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;

public class CuratorClientFactory {

  private String path = "127.0.0.1:2181";
  private int sleepMsBetweenRetries = 100;
  private int maxRetries = 30;

  @Produces
  public CuratorFramework newClient() {
    RetryPolicy retryPolicy = new RetryNTimes(maxRetries, sleepMsBetweenRetries);
    CuratorFramework client = CuratorFrameworkFactory.newClient(path, retryPolicy);
    return client;
  }

  public CuratorFramework newStartedClient() {
    CuratorFramework client = newClient();
    client.start();

    return client;
  }
}
