package de.sirvincelot24.mantle_reforged.block.entity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.client.model.data.IModelData;
import de.sirvincelot24.mantle_reforged.block.RetexturedBlock;
import de.sirvincelot24.mantle_reforged.client.model.data.SinglePropertyData;
import de.sirvincelot24.mantle_reforged.util.RetexturedHelper;

/**
 * Standard interface that should be used by retexturable tile entities, allows control over where the texture is saved.
 * Note that in the future, more of these methods will be made abstract, discouraging the use of {@link #getTileData()} to store the texture (as we can sync our own tag easier)
 *
 * Use alongside {@link RetexturedBlock} and {@link de.sirvincelot24.mantle_reforged.item.RetexturedBlockItem}. See {@link DefaultRetexturedBlockEntity} for implementation.
 */
public interface IRetexturedBlockEntity {
  /* Gets the Forge tile data for the tile entity */
  CompoundTag getTileData();

  /**
   * Gets the current texture block name. Encouraged to override this to not use {@link #getTileData()}
   * @return Texture block name
   */
  default String getTextureName() {
    return RetexturedHelper.getTextureName(getTileData());
  }

  /**
   * Gets the current texture block
   * @return Texture block
   */
  default Block getTexture() {
    return RetexturedHelper.getBlock(getTextureName());
  }

  /**
   * Updates the texture to the given name. Encouraged to override this to not use {@link #getTileData()}
   * @param name  Texture name
   */
  default void updateTexture(String name) {
    RetexturedHelper.setTexture(getTileData(), name);
  }

  /**
   * Gets the model data instance with the relevant texture block
   * @return  Model data for the TE
   */
  default IModelData getRetexturedModelData() {
    // texture not loaded
    Block block = getTexture();
    // cannot support air, saves a conditional on usage
    if (block == Blocks.AIR) {
      block = null;
    }
    return new SinglePropertyData<>(RetexturedHelper.BLOCK_PROPERTY, block);
  }
}
