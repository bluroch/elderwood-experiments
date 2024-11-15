package elderwood.experiments.entity.loader;

import lombok.extern.slf4j.Slf4j;
import net.kyori.adventure.nbt.BinaryTag;
import net.kyori.adventure.nbt.CompoundBinaryTag;
import net.kyori.adventure.nbt.DoubleBinaryTag;
import net.kyori.adventure.nbt.FloatBinaryTag;
import net.kyori.adventure.nbt.ListBinaryTag;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.coordinate.Vec;
import net.minestom.server.entity.Entity;
import net.minestom.server.entity.EntityType;
import net.minestom.server.entity.LivingEntity;
import net.minestom.server.entity.attribute.Attribute;
import net.minestom.server.entity.metadata.other.ArmorStandMeta;
import net.minestom.server.instance.Instance;
import net.minestom.server.item.ItemStack;

@Slf4j
public class ArmorStandLoader {

  private static Vec floatVecFromlist(ListBinaryTag list) {
    if (list == null || list.size() == 0) {
      return new Vec(0d, 0d, 0d);
    }
    var x = list.getFloat(0);
    var y = list.getFloat(1);
    var z = list.getFloat(2);
    return new Vec(x, y, z);
  }

  private static void setRotations(ArmorStandMeta meta, CompoundBinaryTag data) {
    var bodyRot = data.getCompound("Pose").getList("Body");
    var headRot = data.getCompound("Pose").getList("Head");

    var leftArmRot = data.getCompound("Pose").getList("LeftArm");
    var rightArmRot = data.getCompound("Pose").getList("RightArm");

    var leftLegRot = data.getCompound("Pose").getList("LeftLeg");
    var rightLegRot = data.getCompound("Pose").getList("RightLeg");
    meta.setBodyRotation(floatVecFromlist(bodyRot));
    meta.setHeadRotation(floatVecFromlist(headRot));
    if (leftArmRot != null && leftArmRot.size() != 0) {
      meta.setLeftArmRotation(floatVecFromlist(leftArmRot));
    }
    if (rightArmRot != null && rightArmRot.size() != 0) {
      meta.setRightArmRotation(floatVecFromlist(rightArmRot));
    }
    if (leftLegRot != null && leftLegRot.size() != 0) {
      meta.setLeftLegRotation(floatVecFromlist(leftLegRot));
    }
    if (rightLegRot != null && rightLegRot.size() != 0) {
      meta.setRightLegRotation(floatVecFromlist(rightLegRot));
    }
  }

  private static void setArmor(LivingEntity entity, CompoundBinaryTag data) {
    var armor = data.getList("ArmorItems");
    if (armor.get(0) != null) {
      var bootsItem = ItemStack.AIR;
      var boots = armor.getCompound(0);
      try {
        bootsItem = ItemStack.fromItemNBT(boots);
      } catch (Exception e) {
      }
      entity.setBoots(bootsItem);
    }
    if (armor.get(1) != null) {
      var leggingsItem = ItemStack.AIR;
      var leggings = armor.getCompound(1);
      try {
        leggingsItem = ItemStack.fromItemNBT(leggings);
      } catch (Exception e) {
      }
      entity.setLeggings(leggingsItem);
    }
    if (armor.get(2) != null) {
      var chestplateItem = ItemStack.AIR;
      var chestplate = armor.getCompound(2);
      try {
        chestplateItem = ItemStack.fromItemNBT(chestplate);
      } catch (Exception e) {
      }
      entity.setChestplate(chestplateItem);
    }
    if (armor.get(3) != null) {
      var helmetItem = ItemStack.AIR;
      var helmet = armor.getCompound(3);
      try {
        helmetItem = ItemStack.fromItemNBT(helmet);
      } catch (Exception e) {
      }
      entity.setHelmet(helmetItem);
    }
  }

  private static void setHandItems(LivingEntity entity, CompoundBinaryTag data) {
    var handItems = data.getList("HandItems");
    if (handItems.get(0) != null) {
      var mainHandItem = ItemStack.AIR;
      var mainHand = handItems.getCompound(0);
      try {
        mainHandItem = ItemStack.fromItemNBT(mainHand);
      } catch (Exception e) {
      }
      entity.setItemInMainHand(mainHandItem);
    }
    if (handItems.get(1) != null) {
      var offHandItem = ItemStack.AIR;
      var offHand = handItems.getCompound(1);
      try {
        offHandItem = ItemStack.fromItemNBT(offHand);
      } catch (Exception e) {
      }
      entity.setItemInOffHand(offHandItem);
    }

    var meta = (ArmorStandMeta) entity.getEntityMeta();
    meta.setHasArms(data.getBoolean("ShowArms"));
  }

  private static void setScale(LivingEntity entity, CompoundBinaryTag data) {
    var attributes = data.getList("attributes");
    for (BinaryTag binaryTag : attributes) {
      var tag = (CompoundBinaryTag) binaryTag;
      if (tag.getString("id").equals("minecraft:generic.scale")) {
        entity.getAttribute(Attribute.GENERIC_SCALE).setBaseValue(tag.getFloat("base"));
        return;
      }
    }
  }

  public static Entity initArmorStand(CompoundBinaryTag data, Instance world) {
    LivingEntity entity = new LivingEntity(EntityType.ARMOR_STAND);
    ArmorStandMeta meta = (ArmorStandMeta) entity.getEntityMeta();

    setRotations(meta, data);
    setArmor(entity, data);
    setHandItems(entity, data);
    setScale(entity, data);
    meta.setHasNoGravity(true);
    meta.setSmall(data.getBoolean("Small"));
    meta.setInvisible(data.getBoolean("Invisible"));

    meta.setHasNoBasePlate(data.getBoolean("NoBasePlate"));

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

    entity.setInstance(world, pos);

    return entity;
  }

}
