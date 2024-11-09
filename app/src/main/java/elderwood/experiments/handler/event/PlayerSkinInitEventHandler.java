package elderwood.experiments.handler.event;

import net.minestom.server.entity.PlayerSkin;
import net.minestom.server.event.player.PlayerSkinInitEvent;

public class PlayerSkinInitEventHandler {
  public static void handle(PlayerSkinInitEvent event) {
    var uuid = event.getPlayer().getUuid().toString();

    PlayerSkin skinFromUuid = PlayerSkin.fromUuid(uuid);
    event.setSkin(skinFromUuid);
  }
}
