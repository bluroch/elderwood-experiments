package elderwood.experiments.world;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import elderwood.experiments.App;
import elderwood.experiments.entity.loader.ArmorStandLoader;
import elderwood.experiments.entity.loader.ItemFrameLoader;
import elderwood.experiments.entity.loader.PaintingLoader;
import lombok.extern.slf4j.Slf4j;
import net.kyori.adventure.nbt.CompoundBinaryTag;
import net.kyori.adventure.nbt.ListBinaryTag;
import net.minestom.server.entity.EntityType;
import net.minestom.server.instance.Chunk;
import net.minestom.server.instance.Instance;
import net.minestom.server.utils.chunk.ChunkUtils;

@Slf4j
public class EntityLoader {
  private static final Path entitiesPath = Path.of(App.WORLD_PATH).resolve("entities");

  private static final EntityCache entityCache = new EntityCache();

  public static void loadEntitiesInChunk(Chunk chunk, Instance instance) {
    var chunkX = chunk.getChunkX();
    var chunkZ = chunk.getChunkZ();

    var regionX = ChunkUtils.toRegionCoordinate(chunkX);
    var regionZ = ChunkUtils.toRegionCoordinate(chunkZ);

    File entityFile = entitiesPath.resolve("r." + regionX + "." + regionZ + ".mca").toFile();

    if (!entityFile.exists()) {
      return;
    }

    try (RegionFile regionFile = new RegionFile(entityFile.toPath())) {
      CompoundBinaryTag data = regionFile.readChunkData(chunkX, chunkZ);
      if (data != null) {
        ListBinaryTag entitiesList = data.getList("Entities");
        log.info("Entities found: {}", entitiesList.size());

        entitiesList.forEach(entity -> {
          CompoundBinaryTag binaryTag = (CompoundBinaryTag) entity;

          String id = binaryTag.getString("id");
          EntityType entityType = EntityType.fromNamespaceId(id);
          // log.info("Entity type: {}", entityType);
          if (List.of(EntityType.ITEM_FRAME, EntityType.ARMOR_STAND, EntityType.PAINTING).contains(entityType)) {
            // log.info("Entity data: {}", binaryTag);
          }
          switch (entityType.toString()) {
            case "minecraft:painting":
              PaintingLoader.initPainting(binaryTag, instance);
              break;
            case "minecraft:item_frame":
              ItemFrameLoader.initItemFrame(binaryTag, instance);
              break;
            case "minecraft:glow_item_frame":
              ItemFrameLoader.initGlowingItemFrame(binaryTag, instance);
              break;
            case "minecraft:armor_stand":
              ArmorStandLoader.initArmorStand(binaryTag, instance);
              break;
            default:
              break;
          }
        });
      }
    } catch (IOException e) {
      log.error("Couldn't read region file\n", e);
    }
  }
}
