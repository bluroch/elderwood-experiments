package elderwood.experiments.handler;

import elderwood.experiments.handler.event.AsyncPlayerConfigurationEventHandler;
import elderwood.experiments.handler.event.PlayerChunkLoadEventHandler;
import elderwood.experiments.handler.event.PlayerDisconnectEventHandler;
import elderwood.experiments.handler.event.PlayerSkinInitEventHandler;
import elderwood.experiments.handler.event.PlayerSpawnEventHandler;
import lombok.extern.slf4j.Slf4j;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent;
import net.minestom.server.event.player.PlayerChunkLoadEvent;
import net.minestom.server.event.player.PlayerDisconnectEvent;
import net.minestom.server.event.player.PlayerSkinInitEvent;
import net.minestom.server.event.player.PlayerSpawnEvent;

@Slf4j
public class EventHandlerRegistry {
  public static void registerAllHandlers(GlobalEventHandler globalEventHandler) {

    globalEventHandler.addListener(AsyncPlayerConfigurationEvent.class, AsyncPlayerConfigurationEventHandler::handle);
    globalEventHandler.addListener(PlayerSpawnEvent.class, PlayerSpawnEventHandler::handle);
    globalEventHandler.addListener(PlayerSkinInitEvent.class, PlayerSkinInitEventHandler::handle);
    globalEventHandler.addListener(PlayerDisconnectEvent.class, PlayerDisconnectEventHandler::handle);
    globalEventHandler.addListener(PlayerChunkLoadEvent.class, PlayerChunkLoadEventHandler::handle);

    log.info("Registered all event handlers");
  }
}
