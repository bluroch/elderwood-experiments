package elderwood.experiments.handler.event;

import elderwood.experiments.App;
import lombok.extern.slf4j.Slf4j;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Player;
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent;
import net.minestom.server.instance.InstanceContainer;

@Slf4j
public class AsyncPlayerConfigurationEventHandler {
  public static final Pos SPAWN = App.SPAWN;

  private static final InstanceContainer instanceContainer = App.instanceContainer;

  public static void handle(AsyncPlayerConfigurationEvent event) {
    final Player player = event.getPlayer();
    event.setSpawningInstance(instanceContainer);
    player.setRespawnPoint(SPAWN);

    log.info("{} joined", player.getUsername());
  }
}
