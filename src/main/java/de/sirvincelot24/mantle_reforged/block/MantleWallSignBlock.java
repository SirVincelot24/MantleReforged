package de.sirvincelot24.mantle_reforged.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.WallSignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;
import de.sirvincelot24.mantle_reforged.block.entity.MantleSignBlockEntity;

public class MantleWallSignBlock extends WallSignBlock {
  public MantleWallSignBlock(Properties props, WoodType type) {
    super(props, type);
  }

  @Override
  public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
    return new MantleSignBlockEntity(pPos, pState);
  }
}
