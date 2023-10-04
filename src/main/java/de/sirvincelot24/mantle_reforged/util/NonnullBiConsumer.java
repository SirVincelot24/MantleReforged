package de.sirvincelot24.mantle_reforged.util;

import javax.annotation.Nonnull;
import java.util.function.BiConsumer;

/**
 * Equivalent to {@link BiConsumer}, except with nonnull contract.
 *
 * @see BiConsumer
 */
@FunctionalInterface
public interface NonnullBiConsumer<T,U> {
  void accept(@Nonnull T t, @Nonnull U u);
}
