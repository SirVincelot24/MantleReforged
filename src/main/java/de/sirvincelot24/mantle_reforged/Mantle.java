package de.sirvincelot24.mantle_reforged;

import net.minecraft.Util;
import net.minecraft.data.DataGenerator;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig.Type;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import de.sirvincelot24.mantle_reforged.block.entity.MantleSignBlockEntity;
import de.sirvincelot24.mantle_reforged.command.MantleCommand;
import de.sirvincelot24.mantle_reforged.config.Config;
import de.sirvincelot24.mantle_reforged.data.predicate.block.BlockPredicate;
import de.sirvincelot24.mantle_reforged.data.predicate.block.SetBlockPredicate;
import de.sirvincelot24.mantle_reforged.data.predicate.block.TagBlockPredicate;
import de.sirvincelot24.mantle_reforged.data.predicate.entity.LivingEntityPredicate;
import de.sirvincelot24.mantle_reforged.data.predicate.entity.MobTypePredicate;
import de.sirvincelot24.mantle_reforged.data.predicate.entity.TagEntityPredicate;
import de.sirvincelot24.mantle_reforged.datagen.MantleFluidTagProvider;
import de.sirvincelot24.mantle_reforged.datagen.MantleFluidTooltipProvider;
import de.sirvincelot24.mantle_reforged.datagen.MantleTags;
import de.sirvincelot24.mantle_reforged.fluid.transfer.EmptyFluidContainerTransfer;
import de.sirvincelot24.mantle_reforged.fluid.transfer.EmptyFluidWithNBTTransfer;
import de.sirvincelot24.mantle_reforged.fluid.transfer.FillFluidContainerTransfer;
import de.sirvincelot24.mantle_reforged.fluid.transfer.FillFluidWithNBTTransfer;
import de.sirvincelot24.mantle_reforged.fluid.transfer.FluidContainerTransferManager;
import de.sirvincelot24.mantle_reforged.item.LecternBookItem;
import de.sirvincelot24.mantle_reforged.loot.MantleLoot;
import de.sirvincelot24.mantle_reforged.network.MantleNetwork;
import de.sirvincelot24.mantle_reforged.recipe.crafting.ShapedFallbackRecipe;
import de.sirvincelot24.mantle_reforged.recipe.crafting.ShapedRetexturedRecipe;
import de.sirvincelot24.mantle_reforged.recipe.helper.TagEmptyCondition;
import de.sirvincelot24.mantle_reforged.recipe.helper.TagPreference;
import de.sirvincelot24.mantle_reforged.recipe.ingredient.FluidContainerIngredient;
import de.sirvincelot24.mantle_reforged.registration.adapter.BlockEntityTypeRegistryAdapter;
import de.sirvincelot24.mantle_reforged.registration.adapter.RegistryAdapter;
import de.sirvincelot24.mantle_reforged.util.OffhandCooldownTracker;

/**
 * Mantle
 *
 * Central mod object for Mantle
 *
 * @author Sunstrike <sun@sunstrike.io>
 */
@Mod(Mantle.modId)
public class Mantle {
  public static final String modId = "mantle_reforged";
  public static final Logger logger = LogManager.getLogger("Mantle");

  /* Instance of this mod, used for grabbing prototype fields */
  public static Mantle instance;

  /* Proxies for sides, used for graphics processing */
  public Mantle() {
    ModLoadingContext.get().registerConfig(Type.CLIENT, Config.CLIENT_SPEC);
    ModLoadingContext.get().registerConfig(Type.SERVER, Config.SERVER_SPEC);

    FluidContainerTransferManager.INSTANCE.init();
    MantleTags.init();

    instance = this;
    IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
    bus.addListener(EventPriority.NORMAL, false, FMLCommonSetupEvent.class, this::commonSetup);
    bus.addListener(EventPriority.NORMAL, false, RegisterCapabilitiesEvent.class, this::registerCapabilities);
    bus.addListener(EventPriority.NORMAL, false, GatherDataEvent.class, this::gatherData);
    bus.addGenericListener(RecipeSerializer.class, this::registerRecipeSerializers);
    bus.addGenericListener(BlockEntityType.class, this::registerBlockEntities);
    bus.addGenericListener(GlobalLootModifierSerializer.class, MantleLoot::registerGlobalLootModifiers);
    MinecraftForge.EVENT_BUS.addListener(EventPriority.NORMAL, false, PlayerInteractEvent.RightClickBlock.class, LecternBookItem::interactWithBlock);
  }

  private void registerCapabilities(RegisterCapabilitiesEvent event) {
    OffhandCooldownTracker.register(event);
  }

  private void commonSetup(final FMLCommonSetupEvent event) {
    MantleNetwork.registerPackets();
    MantleCommand.init();
    OffhandCooldownTracker.init();
    TagPreference.init();
  }


  private void registerRecipeSerializers(final RegisterEvent event ){
  event.register(ForgeRegistries.Keys.RECIPE_SERIALIZERS, recipeSerializerRegisterHelper -> {
    recipeSerializerRegisterHelper.register("crafting_shaped_retextured", new ShapedRetexturedRecipe.Serializer());
    recipeSerializerRegisterHelper.register("crafting_shaped_fallback", new ShapedFallbackRecipe.Serializer());

    CraftingHelper.register(TagEmptyCondition.SERIALIZER);
    CraftingHelper.register(FluidContainerIngredient.ID, FluidContainerIngredient.SERIALIZER);

    // fluid container transfer
    FluidContainerTransferManager.TRANSFER_LOADERS.registerDeserializer(EmptyFluidContainerTransfer.ID, EmptyFluidContainerTransfer.DESERIALIZER);
    FluidContainerTransferManager.TRANSFER_LOADERS.registerDeserializer(FillFluidContainerTransfer.ID, FillFluidContainerTransfer.DESERIALIZER);
    FluidContainerTransferManager.TRANSFER_LOADERS.registerDeserializer(EmptyFluidWithNBTTransfer.ID, EmptyFluidWithNBTTransfer.DESERIALIZER);
    FluidContainerTransferManager.TRANSFER_LOADERS.registerDeserializer(FillFluidWithNBTTransfer.ID, FillFluidWithNBTTransfer.DESERIALIZER);

    // predicates
    {
      // block predicates
      BlockPredicate.LOADER.register(getResource("and"), BlockPredicate.AND);
      BlockPredicate.LOADER.register(getResource("or"), BlockPredicate.OR);
      BlockPredicate.LOADER.register(getResource("inverted"), BlockPredicate.INVERTED);
      BlockPredicate.LOADER.register(getResource("requires_tool"), BlockPredicate.REQUIRES_TOOL.getLoader());
      BlockPredicate.LOADER.register(getResource("set"), SetBlockPredicate.LOADER);
      BlockPredicate.LOADER.register(getResource("tag"), TagBlockPredicate.LOADER);
      // entity predicates
      LivingEntityPredicate.LOADER.register(getResource("and"), LivingEntityPredicate.AND);
      LivingEntityPredicate.LOADER.register(getResource("or"), LivingEntityPredicate.OR);
      LivingEntityPredicate.LOADER.register(getResource("inverted"), LivingEntityPredicate.INVERTED);
      LivingEntityPredicate.LOADER.register(getResource("any"), LivingEntityPredicate.ANY.getLoader());
      LivingEntityPredicate.LOADER.register(getResource("fire_immune"), LivingEntityPredicate.FIRE_IMMUNE.getLoader());
      LivingEntityPredicate.LOADER.register(getResource("water_sensitive"), LivingEntityPredicate.WATER_SENSITIVE.getLoader());
      LivingEntityPredicate.LOADER.register(getResource("on_fire"), LivingEntityPredicate.ON_FIRE.getLoader());
      LivingEntityPredicate.LOADER.register(getResource("tag"), TagEntityPredicate.LOADER);
      LivingEntityPredicate.LOADER.register(getResource("mob_type"), MobTypePredicate.LOADER);
      // register mob types
      MobTypePredicate.MOB_TYPES.register(new ResourceLocation("undefined"), MobType.UNDEFINED);
      MobTypePredicate.MOB_TYPES.register(new ResourceLocation("undead"), MobType.UNDEAD);
      MobTypePredicate.MOB_TYPES.register(new ResourceLocation("arthropod"), MobType.ARTHROPOD);
      MobTypePredicate.MOB_TYPES.register(new ResourceLocation("illager"), MobType.ILLAGER);
      MobTypePredicate.MOB_TYPES.register(new ResourceLocation("water"), MobType.WATER);
    }
  });
  };


  private void registerBlockEntities(final RegisterEvent event){
    event.register(ForgeRegistries.Keys.BLOCK_ENTITY_TYPES, blockEntityTypeRegisterHelper -> {
      blockEntityTypeRegisterHelper.register(MantleSignBlockEntity::new, "sign", MantleSignBlockEntity::buildSignBlocks);
    });
  }


  private void gatherData(final GatherDataEvent event) {
    DataGenerator generator = event.getGenerator();
    if (event.includeServer()) {
      generator.addProvider(new MantleFluidTagProvider(generator, event.getExistingFileHelper()));
    }
    if (event.includeClient()) {
      generator.addProvider(new MantleFluidTooltipProvider(generator));
    }
  }

  ///**
  // * Gets a resource location for Mantle
  // * @param name  Name
  // * @return  Resource location instance
  // */

  public static ResourceLocation getResource(String name){
    return  new ResourceLocation(modId, name);
  }

  /**
   * Makes a translation key for the given name
   * @param base  Base name, such as "block" or "gui"
   * @param name  Object name
   * @return  Translation key
   */
  public static String makeDescriptionId(String base, String name) {
    return Util.makeDescriptionId(base, getResource(name));
  }

  /**
   * Makes a translation text component for the given name
   * @param base  Base name, such as "block" or "gui"
   * @param name  Object name
   * @return  Translation key
   */
  public static MutableComponent makeComponent(String base, String name) {
    return Component.translatable(makeDescriptionId(base, name));
  }
}
