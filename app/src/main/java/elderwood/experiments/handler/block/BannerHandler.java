package elderwood.experiments.handler.block;

import java.util.Collection;
import java.util.Set;

import org.jetbrains.annotations.NotNull;

import net.minestom.server.instance.block.BlockHandler;
import net.minestom.server.tag.Tag;
import net.minestom.server.utils.NamespaceID;

public class BannerHandler implements BlockHandler {
  public static final NamespaceID ID = NamespaceID.from("minecraft:banner");

  @Override
  public @NotNull NamespaceID getNamespaceId() {
    return ID;
  }

  @Override
  public @NotNull Collection<Tag<?>> getBlockEntityTags() {
    return Set.of(
        Tag.NBT("patterns"));
  }

}
