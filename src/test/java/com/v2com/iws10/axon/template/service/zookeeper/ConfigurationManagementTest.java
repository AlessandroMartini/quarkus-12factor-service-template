package com.v2com.iws10.axon.template.service.zookeeper;

import static com.jayway.awaitility.Awaitility.await;
import static org.assertj.core.api.Assertions.assertThat;

import io.quarkus.test.junit.QuarkusTest;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.x.async.AsyncCuratorFramework;
import org.junit.jupiter.api.Test;


@QuarkusTest
public class ConfigurationManagementTest extends BaseZkTest {

  private static final String KEY_FORMAT = "/%s";

  @Test
  public void givenPath_whenCreateKey_thenValueIsStored() {
    try (CuratorFramework client = newClient()) {
      client.start();
      AsyncCuratorFramework async = AsyncCuratorFramework.wrap(client);
      String key = getKey();
      String expected = "my_value";

      // Create key nodes structure
      client.create()
          .forPath(key);

      // Set data value for our key
      async.setData()
          .forPath(key, expected.getBytes());

      // Get data value
      AtomicBoolean isEquals = new AtomicBoolean();
      async.getData()
          .forPath(key)
          .thenAccept(
              data -> isEquals.set(new String(data).equals(expected)));

      await().until(() -> assertThat(isEquals.get()).isTrue());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  public void givenPath_whenWatchAKeyAndStoreAValue_thenWatcherIsTriggered()
      throws Exception {
    try (CuratorFramework client = newClient()) {
      client.start();
      AsyncCuratorFramework async = AsyncCuratorFramework.wrap(client);
      String key = getKey();
      String expected = "my_value";

      // Create key structure
      async.create()
          .forPath(key);

      List<String> changes = new ArrayList<>();

      // Watch data value
      async.watched()
          .getData()
          .forPath(key)
          .event()
          .thenAccept(watchedEvent -> {
            try {
              changes.add(new String(client.getData()
                  .forPath(watchedEvent.getPath())));
            } catch (Exception e) {
              // fail ...
            }
          });

      // Set data value for our key
      async.setData()
          .forPath(key, expected.getBytes());

      await().until(() -> assertThat(changes.size() > 0).isTrue());
    }
  }

  private String getKey() {
    return String.format(KEY_FORMAT, UUID.randomUUID()
        .toString());
  }
}
