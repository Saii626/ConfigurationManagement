package ConfigurationManagement.impl.ConfigManager;

import ConfigurationManagement.ConfigKey;
import ConfigurationManagement.ConfigurationFileManager;
import ConfigurationManagement.ConfigurationManager;
import ConfigurationManagement.OnConfigurationChange;
import ConfigurationManagement.SerializerDeserializerClassMismatchException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ConfigurationManagerImpl implements ConfigurationManager {


    private Map<ConfigKey, List<OnConfigurationChange>> observerMap;
    private Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());
    private Map<ConfigKey, Object> inMemoryConfigurations;

    private ConfigurationFileManager configurationFileManager;

    public ConfigurationManagerImpl(ConfigurationFileManager configurationFileManager) {
        this.configurationFileManager = configurationFileManager;

        observerMap = new HashMap<>();

        try {
            inMemoryConfigurations = configurationFileManager.getConfigurations();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public <T> Optional<T> get(ConfigKey key, Class<T> deserializer)
            throws SerializerDeserializerClassMismatchException {

        if (key.getSerializer().equals(deserializer)) {
            return Optional.ofNullable(deserializer.cast(inMemoryConfigurations.get(key)));
        } else throw new SerializerDeserializerClassMismatchException(key.getSerializer(), deserializer);
    }

    @Override
    public <T> void put(ConfigKey key, Class<T> deserializer, T value)
            throws SerializerDeserializerClassMismatchException {

        logger.debug("Updating {}: {}", key.getKey(), value.toString());
        if (key.getSerializer().equals(deserializer)) {
            this.notifyForKey(key, deserializer, value);
            inMemoryConfigurations.put(key, value);
        } else throw new SerializerDeserializerClassMismatchException(key.getSerializer(), deserializer);
    }

    @Override
    public void put(ConfigKey key) {
        logger.debug("Deleting {}", key.getKey() );
        inMemoryConfigurations.put(key, null);
    }

    @Override
    public <T> void addOnConfigurationChangeListener(ConfigKey key,
                                                     Class<T> deserializer,
                                                     OnConfigurationChange<T> listener)
            throws SerializerDeserializerClassMismatchException {

        if (key.getSerializer().equals(deserializer)) {
            observerMap.compute(key, (key1, observers) -> {
                if (observers == null) {
                    observers = new ArrayList<>();
                }

                logger.debug("Adding observer for: {}", key.getKey());
                observers.add(listener);
                return observers;
            });
        } else throw new SerializerDeserializerClassMismatchException(key.getSerializer(), deserializer);
    }

    @Override
    public <T> void removeOnConfigurationChangeListener(ConfigKey key,
                                                        Class<T> deserializer,
                                                        OnConfigurationChange<T> listener)
            throws SerializerDeserializerClassMismatchException {

        if (key.getSerializer().equals(deserializer)) {
            observerMap.computeIfPresent(key, (key1, observers) -> {
                logger.debug("Removing observer for: {}", key.getKey());
                observers.remove(listener);
                return observers;
            });
        } else throw new SerializerDeserializerClassMismatchException(key.getSerializer(), deserializer);
    }

    @Override
    public synchronized void sync() throws IOException {
        this.configurationFileManager.sync();
    }

    private <T> void notifyForKey(ConfigKey key, Class<T> deserializer, T newValue)
            throws SerializerDeserializerClassMismatchException {

        if (key.getSerializer().equals(deserializer)) {
            if (observerMap.get(key) != null) {
                observerMap.get(key).forEach(listener -> {
                    try {
                        listener.onConfigurationChange(get(key, deserializer).orElse(null), newValue);
                    } catch (SerializerDeserializerClassMismatchException e) {
                        e.printStackTrace();
                    }
                });
            }
        }else throw  new SerializerDeserializerClassMismatchException(key.getSerializer(), deserializer);
    }
}
