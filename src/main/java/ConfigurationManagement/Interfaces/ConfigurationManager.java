package ConfigurationManagement.Interfaces;

import ConfigurationManagement.MissingConfigurationValue;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.Optional;

public interface ConfigurationManager {

    <T> Optional<T> get(String key);

    <T> T getOrThrow(String key) throws MissingConfigurationValue;

    <T> void put(String key, T value);

    void delete(String key);

    void addOnConfigurationChangeListener(String key, WeakReference<OnConfigurationChange> listener);

    void syncConfigurations() throws IOException;
}
