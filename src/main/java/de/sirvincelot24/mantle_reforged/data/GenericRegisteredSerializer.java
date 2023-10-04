package de.sirvincelot24.mantle_reforged.data;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonSyntaxException;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import de.sirvincelot24.mantle_reforged.data.GenericRegisteredSerializer.IJsonSerializable;
import de.sirvincelot24.mantle_reforged.util.JsonHelper;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * This is a serialier/deserializer to/from JsonObjects that automatically handles dispatching responsibilities to named serializers
 * @param <T>
 */
public class GenericRegisteredSerializer<T extends IJsonSerializable> implements JsonSerializer<T>, JsonDeserializer<T> {
  /** Map of all serializers for implementations */
  private final Map<ResourceLocation,JsonDeserializer<? extends T>> deserializers = new HashMap<>();

  /** Registers a deserializer by name */
  public void registerDeserializer(ResourceLocation name, JsonDeserializer<? extends T> jsonDeserializer) {
    deserializers.put(name, jsonDeserializer);
  }

  @Override
  public T deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
    JsonObject object = GsonHelper.convertToJsonObject(json, "transformer");
    ResourceLocation type = JsonHelper.getResourceLocation(object, "type");
    JsonDeserializer<? extends T> deserializer = deserializers.get(type);
    if (deserializer == null) {
      throw new JsonSyntaxException("Unknown serializer " + type);
    }
    return deserializer.deserialize(json, typeOfT, context);
  }

  @Override
  public JsonElement serialize(T src, Type typeOfSrc, JsonSerializationContext context) {
    JsonObject serialized = src.serialize(context);
    if (!serialized.has("type")) {
      throw new IllegalArgumentException("Invalid serialized sprite transformer, missing type");
    }
    String typeStr = GsonHelper.getAsString(serialized, "type");
    ResourceLocation typeRL = ResourceLocation.tryParse(typeStr);
    if (typeRL == null) {
      throw new IllegalArgumentException("Invalid sprite transformer type '" + typeStr + '\'');
    }
    if (!deserializers.containsKey(typeRL)) {
      throw new IllegalArgumentException("Unregistered sprite transformer " + typeStr);
    }
    return serialized;
  }

  /** Interface to make a generic interface serializable */
  public interface IJsonSerializable {
    /** Serializes this object */
    JsonObject serialize(JsonSerializationContext context);
  }
}
