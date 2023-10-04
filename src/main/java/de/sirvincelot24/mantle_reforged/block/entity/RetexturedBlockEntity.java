package de.sirvincelot24.mantle_reforged.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.common.util.Lazy;
import de.sirvincelot24.mantle_reforged.util.RetexturedHelper;

import javax.annotation.Nonnull;

import static de.sirvincelot24.mantle_reforged.util.RetexturedHelper.TAG_TEXTURE;

/**
 *  Minimal implementation of retextured blocks by storing data in the block entity. Does not handle syncing the best
 * @deprecated use {@link DefaultRetexturedBlockEntity}
 */
@Deprecated
public class RetexturedBlockEntity extends MantleBlockEntity implements IRetexturedBlockEntity {
  /** Lazy value of model data as it will not change after first fetch */
  private final Lazy<IModelData> data = Lazy.of(this::getRetexturedModelData);
  public RetexturedBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
    super(type, pos, state);
  }

  @Nonnull
  @Override
  public IModelData getModelData() {
    return data.get();
  }

  @Override
  protected boolean shouldSyncOnUpdate() {
    return true;
  }

  @Override
  protected void saveSynced(CompoundTag nbt) {
    super.saveSynced(nbt);
    // ensure the texture syncs, by default forge data does not
    if (!nbt.contains("ForgeData")) {
      CompoundTag forgeData = new CompoundTag();
      forgeData.putString(TAG_TEXTURE, getTextureName());
      nbt.put("ForgeData", forgeData);
    }
  }

  @Override
  public void load(CompoundTag tags) {
    String oldName = getTextureName();
    super.load(tags);
    String newName = getTextureName();
    // if the texture name changed, mark the position for rerender
    if (!oldName.equals(newName) && level != null && level.isClientSide) {
      data.get().setData(RetexturedHelper.BLOCK_PROPERTY, getTexture());
      requestModelDataUpdate();
      BlockState state = getBlockState();
      level.sendBlockUpdated(worldPosition, state, state, 0);
    }
  }
}
