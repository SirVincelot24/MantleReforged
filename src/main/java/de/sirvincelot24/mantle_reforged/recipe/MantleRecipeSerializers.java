package de.sirvincelot24.mantle_reforged.recipe;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.ObjectHolder;
import de.sirvincelot24.mantle_reforged.Mantle;

import static de.sirvincelot24.mantle_reforged.registration.RegistrationHelper.injected;

/**
 * All recipe serializers registered under Mantles name
 */
@ObjectHolder(Mantle.modId)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MantleRecipeSerializers {
  public static final RecipeSerializer<?> CRAFTING_SHAPED_FALLBACK = injected();
  public static final RecipeSerializer<?> CRAFTING_SHAPED_RETEXTURED = injected();
}
