package elderwood.experiments.handler.block;

import org.jetbrains.annotations.NotNull;

import net.minestom.server.instance.block.BlockHandler;
import net.minestom.server.utils.NamespaceID;

public class CrafterHandler implements BlockHandler {
  public static final NamespaceID ID = NamespaceID.from("minecraft:crafter");

  @Override
  public @NotNull NamespaceID getNamespaceId() {
    return ID;
  }

}
