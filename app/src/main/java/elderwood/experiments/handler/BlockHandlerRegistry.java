package elderwood.experiments.handler;

import elderwood.experiments.handler.block.*;
import lombok.extern.slf4j.Slf4j;
import net.minestom.server.MinecraftServer;
import net.minestom.server.instance.block.BlockManager;

@Slf4j
public class BlockHandlerRegistry {
  public static void registerAllHandlers() {
    final BlockManager blockHandler = MinecraftServer.getBlockManager();

    blockHandler.registerHandler(BrewingStandHandler.ID, BrewingStandHandler::new);
    blockHandler.registerHandler(HangingSignHandler.ID, HangingSignHandler::new);
    blockHandler.registerHandler(PlayerHeadHandler.ID, PlayerHeadHandler::new);
    blockHandler.registerHandler(ShulkerBoxHandler.ID, ShulkerBoxHandler::new);
    blockHandler.registerHandler(CampfireHandler.ID, CampfireHandler::new);
    blockHandler.registerHandler(ConduitHandler.ID, ConduitHandler::new);
    blockHandler.registerHandler(CrafterHandler.ID, CrafterHandler::new);
    blockHandler.registerHandler(LecternHandler.ID, LecternHandler::new);
    blockHandler.registerHandler(BannerHandler.ID, BannerHandler::new);
    blockHandler.registerHandler(VaultHandler.ID, VaultHandler::new);
    blockHandler.registerHandler(SignHandler.ID, SignHandler::new);

    log.info("All handlers registered");
  }
}
