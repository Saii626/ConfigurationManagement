package ConfigurationManagement;

import java.io.IOException;
import java.util.Optional;

public interface ConfigurationManager {

    <T> Optional<T> get(ConfigKey key, Class<T> deserializer) throws SerializerDeserializerClassMismatchException;

    <T> void put(ConfigKey key, Class<T> deserializer, T value) throws SerializerDeserializerClassMismatchException;

    void put(ConfigKey key);

    <T> void addOnConfigurationChangeListener(ConfigKey key, Class<T> deserializer, OnConfigurationChange<T> listener) throws SerializerDeserializerClassMismatchException;

    <T> void removeOnConfigurationChangeListener(ConfigKey key, Class<T> deserializer, OnConfigurationChange<T> listener) throws SerializerDeserializerClassMismatchException;

    void sync() throws IOException;
}
