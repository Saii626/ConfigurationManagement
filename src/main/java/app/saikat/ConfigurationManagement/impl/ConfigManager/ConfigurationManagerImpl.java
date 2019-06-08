package app.saikat.ConfigurationManagement.impl.ConfigManager;

import app.saikat.ConfigurationManagement.interfaces.ConfigurationFileManager;
import app.saikat.ConfigurationManagement.interfaces.ConfigurationManager;
import app.saikat.ConfigurationManagement.MissingConfigurationValue;
import app.saikat.ConfigurationManagement.interfaces.OnConfigurationChange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class ConfigurationManagerImpl implements ConfigurationManager {

    private Map<String, List<WeakReference<OnConfigurationChange>>> observerMap;
    private Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());
    private final Map<String, Object> configurations;
    private ConfigurationFileManager fileManager;

    public ConfigurationManagerImpl(ConfigurationFileManager fileManager) throws IOException {
        observerMap = new HashMap<>();
        this.fileManager = fileManager;
        this.configurations = this.fileManager.readConfigurations();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> Optional<T> get(String key) {
        synchronized (configurations) {
            return Optional.ofNullable((T) configurations.get(key));
        }
    }

    @Override
    public <T> T getOrThrow(String key) throws MissingConfigurationValue {
       return this.<T>get(key).orElseThrow(() -> new MissingConfigurationValue(key));
    }

    @Override
    public <T> T getRaw(String key) {
        return this.<T>get(key).orElse(null);
    }

    @Override
    public <T> T getOrSetDefault(String key, T defaultValue) throws IOException {
        if (this.<T>get(key).isPresent()) {
            return this.<T>get(key).orElse(defaultValue);
        } else {
            this.put(key, defaultValue);
            this.syncConfigurations();
            return defaultValue;
        }
    }

    @Override
    public <T> void put(String key, T value) {
        synchronized (configurations) {
            logger.debug("Updating {}: {}", key, value);
            this.notifyForKey(key, value);
            configurations.put(key, value);
        }
    }

    @Override
    public void delete(String key) {
        synchronized (configurations) {
            logger.debug("Deleting {}", key);
            this.notifyForKey(key, null);
            configurations.remove(key);
        }
    }

    @Override
    public void addOnConfigurationChangeListener(String key, WeakReference<OnConfigurationChange> listener) {
        observerMap.compute(key, (key1, observers) -> {
            if (observers == null) {
                observers = new ArrayList<>();
            }

            logger.debug("Adding observer for: {}", key);
            observers.add(listener);
            return observers;
        });
    }

    @Override
    public <T> void addOnConfigurationChangeListener(String key, OnConfigurationChange<T> listener) {
        WeakReference<OnConfigurationChange> weakReference = new WeakReference<>(listener);
        addOnConfigurationChangeListener(key, weakReference);
    }

    @Override
    public void syncConfigurations() throws IOException {
        fileManager.writeConfigurations(configurations);
    }

    @SuppressWarnings({"unchecked", "ConstantConditions"})
    private <T> void notifyForKey(String key, T newValue) {
        if (observerMap.get(key) != null) {
            observerMap.get(key).stream()
                    .filter(listener -> listener.get()!=null)
                    .forEach(listener -> listener.get().onConfigurationChange(get(key).orElse(null), newValue));

            List<WeakReference<OnConfigurationChange>> updatedList = observerMap.get(key).stream()
                    .filter(listener -> listener.get()!=null)
                    .collect(Collectors.toList());

            observerMap.put(key, updatedList);
        }
    }
}
