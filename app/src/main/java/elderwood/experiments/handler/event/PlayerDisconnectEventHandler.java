package elderwood.experiments.handler.event;

import lombok.extern.slf4j.Slf4j;
import net.minestom.server.event.player.PlayerDisconnectEvent;

@Slf4j
public class PlayerDisconnectEventHandler {
  public static void handle(PlayerDisconnectEvent event) {
    final var player = event.getPlayer();

    log.info("{} disconnected", player.getUsername());
  }
}
