package de.sirvincelot24.mantle_reforged.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.FluidTagsProvider;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;
import de.sirvincelot24.mantle_reforged.Mantle;

import static de.sirvincelot24.mantle_reforged.datagen.MantleTags.Fluids.LAVA;
import static de.sirvincelot24.mantle_reforged.datagen.MantleTags.Fluids.WATER;

/** Provider for tags added by mantle, generally not useful for other mods */
public class MantleFluidTagProvider extends FluidTagsProvider {
  public MantleFluidTagProvider(DataGenerator gen, @Nullable ExistingFileHelper existingFileHelper) {
    super(gen, Mantle.modId, existingFileHelper);
  }

  @Override
  protected void addTags() {
    this.tag(WATER).add(Fluids.WATER, Fluids.FLOWING_WATER);
    this.tag(LAVA).add(Fluids.LAVA, Fluids.FLOWING_LAVA);
  }

  @Override
  public String getName() {
    return "Mantle Fluid Tag Provider";
  }
}
