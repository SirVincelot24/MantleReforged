package slimeknights.mantle.data.predicate.block;

import net.minecraft.world.level.block.state.BlockState;
import slimeknights.mantle.data.GenericLoaderRegistry;
import slimeknights.mantle.data.GenericLoaderRegistry.IGenericLoader;
import slimeknights.mantle.data.predicate.AndJsonPredicate;
import slimeknights.mantle.data.predicate.IJsonPredicate;
import slimeknights.mantle.data.predicate.InvertedJsonPredicate;
import slimeknights.mantle.data.predicate.NestedJsonPredicateLoader;
import slimeknights.mantle.data.predicate.OrJsonPredicate;

import static slimeknights.mantle.data.GenericLoaderRegistry.SingletonLoader.singleton;

/**
 * Simple serializable block predicate
 */
public interface BlockPredicate extends IJsonPredicate<BlockState> {
  /** Loader for block state predicates */
  GenericLoaderRegistry<IJsonPredicate<BlockState>> LOADER = new GenericLoaderRegistry<>(true);
  /** Loader for inverted conditions */
  InvertedJsonPredicate.Loader<BlockState> INVERTED = new InvertedJsonPredicate.Loader<>(LOADER);
  /** Loader for and conditions */
  NestedJsonPredicateLoader<BlockState,AndJsonPredicate<BlockState>> AND = AndJsonPredicate.createLoader(LOADER, INVERTED);
  /** Loader for or conditions */
  NestedJsonPredicateLoader<BlockState,OrJsonPredicate<BlockState>> OR = OrJsonPredicate.createLoader(LOADER, INVERTED);

  /** Gets an inverted condition */
  @Override
  default IJsonPredicate<BlockState> inverted() {
    return INVERTED.create(this);
  }


  /* Singleton */

  /** Predicate that matches blocks with no harvest tool */
  BlockPredicate REQUIRES_TOOL = singleton(loader -> new BlockPredicate() {
    @Override
    public boolean matches(BlockState input) {
      return input.requiresCorrectToolForDrops();
    }

    @Override
    public IGenericLoader<? extends IJsonPredicate<BlockState>> getLoader() {
      return loader;
    }
  });
}
