package elderwood.experiments.handler.event;

import elderwood.experiments.world.EntityLoader;
import net.minestom.server.event.player.PlayerChunkLoadEvent;

public class PlayerChunkLoadEventHandler {
  public static void handle(PlayerChunkLoadEvent event) {
    var instance = event.getInstance();
    var chunk = instance.getChunk(event.getChunkX(), event.getChunkZ());

    EntityLoader.loadEntitiesInChunk(chunk, instance);
  }
}
