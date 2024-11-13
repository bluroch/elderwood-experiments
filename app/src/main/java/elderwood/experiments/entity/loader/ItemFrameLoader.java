package elderwood.experiments.entity.loader;

import lombok.extern.slf4j.Slf4j;
import net.kyori.adventure.nbt.CompoundBinaryTag;
import net.kyori.adventure.nbt.DoubleBinaryTag;
import net.kyori.adventure.nbt.FloatBinaryTag;
import net.kyori.adventure.nbt.ListBinaryTag;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Entity;
import net.minestom.server.entity.EntityType;
import net.minestom.server.entity.metadata.other.ItemFrameMeta;
import net.minestom.server.entity.metadata.other.ItemFrameMeta.Orientation;
import net.minestom.server.instance.Instance;
import net.minestom.server.item.ItemStack;
import net.minestom.server.utils.Rotation;

@Slf4j
public class ItemFrameLoader {
  private static Orientation getOrientation(CompoundBinaryTag data) {
    int num = data.getByte("facing");
    Orientation[] list = {
        Orientation.SOUTH,
        Orientation.WEST,
        Orientation.NORTH,
        Orientation.EAST };
    return list[num];
  }

  private static boolean isInvisible(CompoundBinaryTag data) {
    return data.getBoolean("Invisible");
  }

  private static Rotation getRotation(CompoundBinaryTag data) {
    byte num = data.getByte("ItemRotation");
    Rotation[] list = {
        Rotation.NONE,
        Rotation.CLOCKWISE_45,
        Rotation.CLOCKWISE,
        Rotation.CLOCKWISE_135,
        Rotation.FLIPPED,
        Rotation.FLIPPED_45,
        Rotation.COUNTER_CLOCKWISE,
        Rotation.COUNTER_CLOCKWISE_45
    };
    return list[num];
  }

  private static ItemStack getItem(CompoundBinaryTag data) {
    var item = ItemStack.AIR;
    try {
      item = ItemStack.fromItemNBT(data.getCompound("Item"));
    } catch (NullPointerException ignored) {
    }
    return item;
  }

  public static Entity initItemFrame(CompoundBinaryTag data, Instance world) {
    Entity itemFrame = new Entity(EntityType.ITEM_FRAME);
    ItemFrameMeta meta = (ItemFrameMeta) itemFrame.getEntityMeta();
    meta.setOrientation(getOrientation(data));
    meta.setRotation(getRotation(data));
    meta.setItem(getItem(data));
    itemFrame.setInvisible(isInvisible(data));

    ListBinaryTag posTag = (ListBinaryTag) data.get("Pos");
    ListBinaryTag rotationTag = (ListBinaryTag) data.get("Rotation");

    double[] posList = new double[] { 0, 0, 0 };
    if (posTag != null) {
      for (int i = 0; i < posTag.size(); i++) {
        DoubleBinaryTag tag = (DoubleBinaryTag) posTag.get(i);
        posList[i] = tag.value();
      }
    }

    float[] rotationList = new float[] { 0, 0 };
    if (rotationTag != null) {
      for (int i = 0; i < rotationTag.size(); i++) {
        FloatBinaryTag tag = (FloatBinaryTag) rotationTag.get(i);
        rotationList[i] = tag.value();
      }
    }

    Pos pos = new Pos(
        posList[0],
        posList[1],
        posList[2],
        rotationList[0],
        rotationList[1]);

    itemFrame.setInstance(world, pos);

    log.info(data.toString());

    // itemFrame.setGlowing(true);

    return itemFrame;
  }

  public static Entity initGlowingItemFrame(CompoundBinaryTag data, Instance world) {
    Entity glowingItemFrame = new Entity(EntityType.GLOW_ITEM_FRAME);
    ItemFrameMeta meta = (ItemFrameMeta) glowingItemFrame.getEntityMeta();
    meta.setOrientation(getOrientation(data));
    meta.setRotation(getRotation(data));
    meta.setItem(getItem(data));
    glowingItemFrame.setInvisible(isInvisible(data));

    ListBinaryTag posTag = (ListBinaryTag) data.get("Pos");
    ListBinaryTag rotationTag = (ListBinaryTag) data.get("Rotation");

    double[] posList = new double[] { 0, 0, 0 };
    if (posTag != null) {
      for (int i = 0; i < posTag.size(); i++) {
        DoubleBinaryTag tag = (DoubleBinaryTag) posTag.get(i);
        posList[i] = tag.value();
      }
    }

    float[] rotationList = new float[] { 0, 0 };
    if (rotationTag != null) {
      for (int i = 0; i < rotationTag.size(); i++) {
        FloatBinaryTag tag = (FloatBinaryTag) rotationTag.get(i);
        rotationList[i] = tag.value();
      }
    }

    Pos pos = new Pos(
        posList[0],
        posList[1],
        posList[2],
        rotationList[0],
        rotationList[1]);

    glowingItemFrame.setInstance(world, pos);

    // glowingItemFrame.setGlowing(true);

    log.info(data.toString());

    return glowingItemFrame;
  }
}
