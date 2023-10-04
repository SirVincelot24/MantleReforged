package de.sirvincelot24.mantle_reforged.data.predicate.entity;

import net.minecraft.world.entity.LivingEntity;
import de.sirvincelot24.mantle_reforged.data.GenericLoaderRegistry;
import de.sirvincelot24.mantle_reforged.data.GenericLoaderRegistry.IGenericLoader;
import de.sirvincelot24.mantle_reforged.data.predicate.AndJsonPredicate;
import de.sirvincelot24.mantle_reforged.data.predicate.IJsonPredicate;
import de.sirvincelot24.mantle_reforged.data.predicate.InvertedJsonPredicate;
import de.sirvincelot24.mantle_reforged.data.predicate.NestedJsonPredicateLoader;
import de.sirvincelot24.mantle_reforged.data.predicate.OrJsonPredicate;

import static de.sirvincelot24.mantle_reforged.data.GenericLoaderRegistry.SingletonLoader.singleton;

/** Predicate matching an entity */
public interface LivingEntityPredicate extends IJsonPredicate<LivingEntity> {
  /** Predicate that matches all entities */
  LivingEntityPredicate ANY = singleton(loader -> new LivingEntityPredicate() {
    @Override
    public boolean matches(LivingEntity input) {
      return true;
    }

    @Override
    public IGenericLoader<? extends IJsonPredicate<LivingEntity>> getLoader() {
      return loader;
    }
  });

  /** Loader for block state predicates */
  GenericLoaderRegistry<IJsonPredicate<LivingEntity>> LOADER = new GenericLoaderRegistry<>(ANY, true);
  /** Loader for inverted conditions */
  InvertedJsonPredicate.Loader<LivingEntity> INVERTED = new InvertedJsonPredicate.Loader<>(LOADER);
  /** Loader for and conditions */
  NestedJsonPredicateLoader<LivingEntity,AndJsonPredicate<LivingEntity>> AND = AndJsonPredicate.createLoader(LOADER, INVERTED);
  /** Loader for or conditions */
  NestedJsonPredicateLoader<LivingEntity,OrJsonPredicate<LivingEntity>> OR = OrJsonPredicate.createLoader(LOADER, INVERTED);

  /** Gets an inverted condition */
  @Override
  default IJsonPredicate<LivingEntity> inverted() {
    return INVERTED.create(this);
  }

  /* Singletons */

  /** Predicate that matches water sensitive entities */
  LivingEntityPredicate WATER_SENSITIVE = singleton(loader -> new LivingEntityPredicate() {
    @Override
    public boolean matches(LivingEntity input) {
      return input.isSensitiveToWater();
    }

    @Override
    public IGenericLoader<? extends IJsonPredicate<LivingEntity>> getLoader() {
      return loader;
    }
  });

  /** Predicate that matches fire immune entities */
  LivingEntityPredicate FIRE_IMMUNE = singleton(loader -> new LivingEntityPredicate() {
    @Override
    public boolean matches(LivingEntity input) {
      return input.fireImmune();
    }

    @Override
    public IGenericLoader<? extends IJsonPredicate<LivingEntity>> getLoader() {
      return loader;
    }
  });

  /** Predicate that matches fire immune entities */
  LivingEntityPredicate ON_FIRE = singleton(loader -> new LivingEntityPredicate() {
    @Override
    public boolean matches(LivingEntity input) {
      return input.isOnFire();
    }

    @Override
    public IGenericLoader<? extends IJsonPredicate<LivingEntity>> getLoader() {
      return loader;
    }
  });
}
