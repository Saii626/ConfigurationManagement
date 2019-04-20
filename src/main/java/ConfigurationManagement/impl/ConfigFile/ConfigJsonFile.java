package ConfigurationManagement.impl.ConfigFile;

import ConfigurationManagement.ConfigKey;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.util.HashMap;
import java.util.Map;

public class ConfigJsonFile {

    private Map<ConfigKey, JsonElement> configuration;

    public ConfigJsonFile() {
        configuration = new HashMap<>();
    }

    public Map<ConfigKey, JsonElement> getConfigMap() {
        return configuration;
    }

    public void setConfigMap(Map<ConfigKey, JsonElement> configuration) {
        this.configuration = configuration;
    }

    public void addConfig(ConfigKey key, JsonElement obj) {
        this.configuration.put(key, obj);
    }

    public Map<ConfigKey, Object> getConfigurations(Gson gson) {

        Map<ConfigKey, Object> map = new HashMap<>();

        for (Map.Entry<ConfigKey, JsonElement> entry: configuration.entrySet()) {
            Object obj = gson.fromJson(entry.getValue(), entry.getKey().getSerializer());
            map.put(entry.getKey(), obj);
        }

        return map;
    }

    public void setConfiguration(Map<ConfigKey, Object> map, Gson gson) {
        this.configuration = new HashMap<>();

        for (Map.Entry<ConfigKey, Object> entry : map.entrySet()) {
            JsonElement element = gson.toJsonTree(entry.getValue(), entry.getKey().getSerializer());
            addConfig(entry.getKey(), element);
        }
    }
}
