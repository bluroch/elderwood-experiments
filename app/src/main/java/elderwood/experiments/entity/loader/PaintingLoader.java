package elderwood.experiments.entity.loader;

import lombok.extern.slf4j.Slf4j;
import net.kyori.adventure.nbt.CompoundBinaryTag;
import net.kyori.adventure.nbt.ListBinaryTag;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.coordinate.Vec;
import net.minestom.server.entity.Entity;
import net.minestom.server.entity.EntityType;
import net.minestom.server.entity.metadata.other.PaintingMeta;
import net.minestom.server.entity.metadata.other.PaintingMeta.Orientation;
import net.minestom.server.entity.metadata.other.PaintingMeta.Variant;
import net.minestom.server.instance.Instance;
import net.minestom.server.registry.DynamicRegistry;
import net.minestom.server.utils.NamespaceID;

@Slf4j
public class PaintingLoader {
  private static DynamicRegistry<Variant> variantRegistry = Variant.createDefaultRegistry();

  private static Vec vecFromList(ListBinaryTag list) {
    var x = list.getDouble(0);
    var y = list.getDouble(1);
    var z = list.getDouble(2);
    return new Vec(x, y, z);
  }

  private static Orientation paintingOrientation(
    CompoundBinaryTag data) {
    int num = data.getByte("facing");
    Orientation[] list = {
        Orientation.SOUTH,
        Orientation.WEST,
        Orientation.NORTH,
        Orientation.EAST };
    return list[num];
  }

  private static Variant paintingVariant(CompoundBinaryTag data) {
    String name = data.getString("variant");
    Variant variant = variantRegistry.get(NamespaceID.from(name));
    log.info("Name {}, variant {}", name, variant.toString());
    return variant;
  }

  private static Pos getPos(CompoundBinaryTag data) {
    var orientation = paintingOrientation(data);
    var ceilZ = orientation == Orientation.WEST || orientation == Orientation.EAST;
    var posVec = vecFromList(data.getList("Pos"));
    var posZ = ceilZ ? Math.ceil(posVec.z()) + 1d : posVec.z();
    return new Pos(posVec.x(), Math.ceil(posVec.y()) - 1d, posZ);
  }

  public static Entity initPainting(CompoundBinaryTag data, Instance world) {
    Entity painting = new Entity(EntityType.PAINTING);
    // Vec pos = PaintingLoader.vecFromList(data.getList("Pos"));
    ListBinaryTag rot = data.getList("Rotation");
    PaintingMeta meta = (PaintingMeta) painting.getEntityMeta();

    Orientation orientation = paintingOrientation(data);
    meta.setOrientation(orientation);

    var variant = PaintingLoader.paintingVariant(data);
    meta.setVariant(variantRegistry.getKey(variant));
    // if (orientation == Orientation.WEST
    //     || orientation == Orientation.EAST) {
    //   pos = pos.withZ(pos.z() + (variant.width() / 32)
    //       * (orientation == Orientation.WEST ? -1 : 0));
    // } else {
    //   pos = pos.withX(pos.x() + (variant.width() / 32)
    //       * (orientation == Orientation.SOUTH ? -1 : 0));
    // }
    // pos = pos.withY(pos.y() - variant.height() / 32);
    var pos = getPos(data);
    painting.setInstance(world, new Pos(pos, rot.getFloat(0), rot.getFloat(1)));
    log.info("Variant: {}, pos: {}", meta.getVariant().toString(), pos);
    // painting.setGlowing(true);

    return painting;
  }
}
