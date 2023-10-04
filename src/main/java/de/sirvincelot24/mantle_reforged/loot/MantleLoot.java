package de.sirvincelot24.mantle_reforged.loot;

import com.google.gson.JsonDeserializer;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.Serializer;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.ObjectHolder;
import de.sirvincelot24.mantle_reforged.Mantle;
import de.sirvincelot24.mantle_reforged.loot.condition.BlockTagLootCondition;
import de.sirvincelot24.mantle_reforged.loot.condition.ContainsItemModifierLootCondition;
import de.sirvincelot24.mantle_reforged.loot.condition.EmptyModifierLootCondition;
import de.sirvincelot24.mantle_reforged.loot.condition.ILootModifierCondition;
import de.sirvincelot24.mantle_reforged.loot.condition.InvertedModifierLootCondition;
import de.sirvincelot24.mantle_reforged.loot.function.RetexturedLootFunction;
import de.sirvincelot24.mantle_reforged.loot.function.SetFluidLootFunction;
import de.sirvincelot24.mantle_reforged.registration.adapter.RegistryAdapter;

import static de.sirvincelot24.mantle_reforged.registration.RegistrationHelper.injected;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@ObjectHolder(value = Mantle.modId)
public class MantleLoot {
  /** Condition to match a block tag and property predicate */
  public static LootItemConditionType BLOCK_TAG_CONDITION;
  /** Function to add block entity texture to a dropped item */
  public static LootItemFunctionType RETEXTURED_FUNCTION;
  /** Function to add a fluid to an item fluid capability */
  public static LootItemFunctionType SET_FLUID_FUNCTION;

  /** Loot modifier to get loot from an entry for generated loot */
  public static final AddEntryLootModifier.Serializer ADD_ENTRY = injected();
  /** Loot modifier to replace all instances of one item with another */
  public static final ReplaceItemLootModifier.Serializer REPLACE_ITEM = injected();

  /**
   * Called during serializer registration to register any relevant loot logic
   */
  public static void registerGlobalLootModifiers(final RegistryEvent.Register<GlobalLootModifierSerializer<?>> event) {
    RegistryAdapter<GlobalLootModifierSerializer<?>> adapter = new RegistryAdapter<>(event.getRegistry());
    adapter.register(new AddEntryLootModifier.Serializer(), "add_entry");
    adapter.register(new ReplaceItemLootModifier.Serializer(), "replace_item");

    // functions
    RETEXTURED_FUNCTION = registerFunction("fill_retextured_block", RetexturedLootFunction.SERIALIZER);
    SET_FLUID_FUNCTION = registerFunction("set_fluid", SetFluidLootFunction.SERIALIZER);

    // conditions
    BLOCK_TAG_CONDITION = registerCondition("block_tag", BlockTagLootCondition.SERIALIZER);

    // loot modifier conditions
    registerCondition(InvertedModifierLootCondition.ID, InvertedModifierLootCondition::deserialize);
    registerCondition(EmptyModifierLootCondition.ID, EmptyModifierLootCondition.INSTANCE);
    registerCondition(ContainsItemModifierLootCondition.ID, ContainsItemModifierLootCondition::deserialize);
  }

  /**
   * Registers a loot function
   * @param name        Loot function name
   * @param serializer  Loot function serializer
   * @return  Registered loot function
   */
  private static LootItemFunctionType registerFunction(String name, Serializer<? extends LootItemFunction> serializer) {
    return Registry.register(Registry.LOOT_FUNCTION_TYPE, Mantle.getResource(name), new LootItemFunctionType(serializer));
  }

  /**
   * Registers a loot function
   * @param name        Loot function name
   * @param serializer  Loot function serializer
   * @return  Registered loot function
   */
  private static LootItemConditionType registerCondition(String name, Serializer<? extends LootItemCondition> serializer) {
    return Registry.register(Registry.LOOT_CONDITION_TYPE, Mantle.getResource(name), new LootItemConditionType(serializer));
  }

  /**
   * Registers a loot condition
   * @param id            Loot condition id
   * @param deserializer  Loot condition deserializer
   */
  private static void registerCondition(ResourceLocation id, JsonDeserializer<? extends ILootModifierCondition> deserializer) {
    ILootModifierCondition.MODIFIER_CONDITIONS.registerDeserializer(id, deserializer);
  }
}
