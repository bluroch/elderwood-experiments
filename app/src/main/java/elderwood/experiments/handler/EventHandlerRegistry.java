package elderwood.experiments.handler;

import elderwood.experiments.handler.event.AsyncPlayerConfigurationEventHandler;
import elderwood.experiments.handler.event.PlayerSkinInitEventHandler;
import elderwood.experiments.handler.event.PlayerSpawnEventHandler;
import net.minestom.server.MinecraftServer;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent;
import net.minestom.server.event.player.PlayerSkinInitEvent;
import net.minestom.server.event.player.PlayerSpawnEvent;

public class EventHandlerRegistry {
  public static void registerAllHandlers() {
    GlobalEventHandler globalEventHandler = MinecraftServer.getGlobalEventHandler();

    globalEventHandler.addListener(AsyncPlayerConfigurationEvent.class, AsyncPlayerConfigurationEventHandler::handle);
    globalEventHandler.addListener(PlayerSpawnEvent.class, PlayerSpawnEventHandler::handle);
    globalEventHandler.addListener(PlayerSkinInitEvent.class, PlayerSkinInitEventHandler::handle);
  }
}
