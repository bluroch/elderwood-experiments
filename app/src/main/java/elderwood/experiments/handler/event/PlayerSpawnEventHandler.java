package elderwood.experiments.handler.event;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.minestom.server.collision.Aerodynamics;
import net.minestom.server.entity.GameMode;
import net.minestom.server.entity.Player;
import net.minestom.server.event.player.PlayerSpawnEvent;

public class PlayerSpawnEventHandler {
  private static MiniMessage mm = MiniMessage.miniMessage();

  private static final String JOIN_MESSAGE = "<red>Welcome <bold><player></bold>!</red>";

  public static void handle(PlayerSpawnEvent event) {
    final Player player = event.getPlayer();
    player.setGameMode(GameMode.CREATIVE);
    player.setAerodynamics(new Aerodynamics(1.0d, 1.0d, 1.0d));
    player.setFlyingSpeed(0.3f);

    if (event.isFirstSpawn()) {
      var playerNameComponent = Component.text(player.getUsername());
      player.sendMessage(mm.deserialize(JOIN_MESSAGE, Placeholder.component("player", playerNameComponent)));
    }
  }
}
