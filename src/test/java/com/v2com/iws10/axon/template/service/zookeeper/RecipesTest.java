package com.v2com.iws10.axon.template.service.zookeeper;

import static com.jayway.awaitility.Awaitility.await;
import static org.assertj.core.api.Assertions.assertThat;

import io.quarkus.test.junit.QuarkusTest;
import java.util.ArrayList;
import java.util.List;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListener;
import org.apache.curator.framework.recipes.locks.InterProcessSemaphoreMutex;
import org.apache.curator.framework.recipes.shared.SharedCount;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.x.async.AsyncCuratorFramework;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class RecipesTest extends BaseZkTest {

  @Test
  public void givenRunningZookeeper_whenUsingLeaderElection_thenNoErrors() {
    try (CuratorFramework client = newClient()) {
      client.start();
      LeaderSelector leaderSelector = new LeaderSelector(client, "/mutex/select/leader/for/Ajob", new LeaderSelectorListener() {

        @Override
        public void stateChanged(CuratorFramework client, ConnectionState newState) {

        }

        @Override
        public void takeLeadership(CuratorFramework client) throws Exception {
          // I'm the leader of the A job!
        }

      });

      leaderSelector.start();

      // Wait until the job A is done among all the members

      leaderSelector.close();
    }
  }

  @Test
  public void givenRunningZookeeper_whenUsingSharedLock_thenNoErrors() throws Exception {
    try (CuratorFramework client = newClient()) {
      client.start();
      InterProcessSemaphoreMutex sharedLock = new InterProcessSemaphoreMutex(client, "/mutex/process/LockableResource");

      sharedLock.acquire();

      // Do process A

      sharedLock.release();
    }
  }

  @Test
  public void givenRunningZookeeper_whenUsingSharedCounter_thenCounterIsIncrement() throws Exception {
    try (CuratorFramework client = newClient()) {
      client.start();

      try (SharedCount counter = new SharedCount(client, "/counters/Total", 0)) {
        counter.start();

        counter.setCount(0);
        counter.setCount(counter.getCount() + 1);

        assertThat(counter.getCount()).isEqualTo(1);
      }

    }
  }

  @Test
  public void givenRunningZookeeper_whenUsingObserver_thenNoErrors() {
    try (CuratorFramework client = newClient()) {
      client.start();
      AsyncCuratorFramework async = AsyncCuratorFramework.wrap(client);
      String key = "/observer";
      String expected = "my_value";

      async.create().forPath(key);

      List<String> changes = new ArrayList<>();

      async.watched()
          .getData()
          .forPath(key)
          .event()
          .thenAccept(watchedEvent -> {
            try {
              changes.add(new String(client.getData()
                  .forPath(watchedEvent.getPath())));
            } catch (Exception e) {
              //fail ...
            }
          });

      async.setData()
          .forPath(key, expected.getBytes());

      await()
          .until(() -> assertThat(changes.size()).isEqualTo(1));
    }
  }

}
