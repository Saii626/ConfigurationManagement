package ConfigurationManagement.impl.ConfigFile;

import ConfigurationManagement.Interfaces.ConfigurationFileManager;
import ConfigurationManagement.Interfaces.Configurations;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ConfigurationsImpl implements Configurations {

    private Map<String, Object> configuration;
    private Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    public ConfigurationsImpl() {
        configuration = new HashMap<>();
    }

    private void putConfig(String key, Object obj) {
        this.configuration.put(key, obj);
    }

    private Object getConfig(String key) {
        return this.configuration.get(key);
    }

    private void removeConfig(String key) {
        this.configuration.remove(key);
    }

    private Map<String, Object> getConfiguration() {
        return configuration;
    }

    // Public APIs
    @Override
    public boolean containsKey(String key) {
        return configuration.containsKey(key);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T get(String key) {
        return (T) getConfig(key);
    }

    @Override
    public <T> void put(String key, T t) {
        putConfig(key, t);
    }

    @Override
    public void delete(String key) {
        removeConfig(key);
    }

    public static class ConfigurationsAdapter implements JsonSerializer<ConfigurationsImpl>, JsonDeserializer<ConfigurationsImpl> {

        private Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

        @Override
        @SuppressWarnings("unchecked")
        public ConfigurationsImpl deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            logger.info("Deserializing JSON");

            ConfigurationsImpl jsonFile = new ConfigurationsImpl();
            Set<Map.Entry<String, JsonElement>> entries = json.getAsJsonObject().entrySet();
            try {

                for (Map.Entry<String, JsonElement> entry : entries) {
                    String keyName = entry.getKey();

                    JsonObject payloadObj = entry.getValue().getAsJsonObject();

                    String type = payloadObj.get("type").getAsString();
                    Class dataType = Class.forName(type);

                    Object obj = context.deserialize(payloadObj.get("data"), TypeToken.get(dataType).getType());
                    logger.debug("Found class: {}, object: {}", dataType.getSimpleName(), obj);

                    jsonFile.putConfig(keyName, obj);
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            return jsonFile;
        }

        @Override
        public JsonElement serialize(ConfigurationsImpl src, Type typeOfSrc, JsonSerializationContext context) {
            logger.info("Serializing configurations");

            JsonObject element = new JsonObject();

            for (Map.Entry<String, Object> entry : src.getConfiguration().entrySet()) {
                String propName = entry.getKey();

                Object data = entry.getValue();
                Class dataType = data.getClass();

                JsonObject jsonObject = new JsonObject();
                jsonObject.add("type", new JsonPrimitive(dataType.getName()));
                jsonObject.add("data", context.serialize(data));

                logger.debug("Adding class: {}, object: {}", dataType.getSimpleName(), data);
                element.add(propName, jsonObject);
            }
            return element;
        }
    }
}
