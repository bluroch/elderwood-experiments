package elderwood.experiments.handler.block;

import org.jetbrains.annotations.NotNull;

import net.minestom.server.instance.block.BlockHandler;
import net.minestom.server.utils.NamespaceID;

public class ConduitHandler implements BlockHandler {
  public static final NamespaceID ID = NamespaceID.from("minecraft:conduit");

  @Override
  public @NotNull NamespaceID getNamespaceId() {
    return ID;
  }

}
