package ConfigurationManagement;

import java.util.Arrays;

public enum ConfigKey {
    PID("pid", Integer.class, null),
    BASE_URL("base_url", String.class, "REMOTE"),
    USER_ID("user_id", String.class, null),
    DEVICE_NAME("device_name", String.class, "test");

    private String key;
    private Class serializer;
    private Object default_value;

    ConfigKey(String key, Class serializer, Object default_value) {
        this.key = key;
        this.serializer = serializer;
        this.default_value = default_value;
    }

    public String getKey() {
        return key;
    }

    public Class getSerializer() {
        return serializer;
    }

    @SuppressWarnings("unchecked")
    public <T> T getDefaultValue() {
        return (T) default_value;
    }

    public static ConfigKey getConfigKeyFromKey(String key) throws NoSuchConfigKeyFound {
        return Arrays.stream(ConfigKey.values()).filter(configKey -> configKey.getKey().equals(key))
                .findFirst().orElseThrow(() -> new NoSuchConfigKeyFound(String.format("No config with %s found", key)));
    }
}
