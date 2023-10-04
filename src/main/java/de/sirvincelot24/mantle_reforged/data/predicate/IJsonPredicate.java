package de.sirvincelot24.mantle_reforged.data.predicate;

import de.sirvincelot24.mantle_reforged.data.GenericLoaderRegistry.IHaveLoader;

/** Generic interface for predicate based JSON loaders */
public interface IJsonPredicate<I> extends IHaveLoader<IJsonPredicate<I>> {
  /** Returns true if this json predicate matches the given input */
  boolean matches(I input);

  /** Inverts the given predicate */
  IJsonPredicate<I> inverted();
}
