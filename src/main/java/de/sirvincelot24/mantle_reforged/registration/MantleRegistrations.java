package de.sirvincelot24.mantle_reforged.registration;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.ObjectHolder;
import de.sirvincelot24.mantle_reforged.Mantle;
import de.sirvincelot24.mantle_reforged.block.entity.MantleSignBlockEntity;

import static de.sirvincelot24.mantle_reforged.registration.RegistrationHelper.injected;

/**
 * Various objects registered under Mantle
 */
@ObjectHolder(Mantle.modId)
public class MantleRegistrations {
  private MantleRegistrations() {}

  public static final BlockEntityType<MantleSignBlockEntity> SIGN = injected();
}
