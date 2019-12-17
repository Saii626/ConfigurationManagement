package app.saikat.ConfigurationManagement.interfaces;

import app.saikat.ConfigurationManagement.MissingConfigurationValue;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.Optional;

/**
 * Main object exported by this submodule. An instance
 * of this class should be the only entry point of this module
 * The class is thread safe
 */
public interface ConfigurationManager {

	/**
	 * Gets an optional configuration object associated with the key
	 * @param key the key whose mapping is required
	 * @param <T> type parameter of associated object
	 * @return optional object. This will have object iff a valid key exists
	 * and its associated value is not null
	 */
	<T> Optional<T> get(String key);


	/**
	 * Gets an configuration object associated with the key
	 * @param key the key whose mapping is required
	 * @param <T> type parameter of associated object
	 * @return configuration object if present
	 * @throws MissingConfigurationValue if either no such key exists or value is null
	 */
	<T> T getOrThrow(String key) throws MissingConfigurationValue;


	/**
	 * Gets an configuration object associated with the key
	 * @param key the key whose mapping is required
	 * @param <T> type parameter of associated object
	 * @return configuration object if present. Else null
	 */
	<T> T getRaw(String key);


	/**
	 * Gets a configuration object associated with key if present
	 * Else puts the default value in the map
	 * @param key the key whose mapping is required
	 * @param defaultValue default value to store and return if object associated
	 *					 with key doesnot exist or is null
	 * @param <T> type parameter of associated object
	 * @return configuration object if present
	 * @throws IOException if unable to store the default value
	 */
	<T> T getOrSetDefault(String key, T defaultValue) throws IOException;


	/**
	 * Associates a configuration object with the key
	 * @param key the key to make association with
	 * @param value the configuration object
	 * @param <T> type parameter of associated object
	 */
	<T> void put(String key, T value);


	/**
	 * Removes a association between key and object
	 * @param key the key whose association to remove
	 */
	void delete(String key);


	/**
	 * Adds a listener which gets called when configuration change occurs. Add observer
	 * to support dynamic update of application
	 * @param key the key whose association to observer
	 * @param listener a WeakReference to OnConfigurationListener instance. This is the callback method
	 */
	void addOnConfigurationChangeListener(String key, WeakReference<OnConfigurationChange<?>> listener);


	/**
	 * Adds a listener which gets called when configuration change occurs. Add observer
	 * to support dynamic update of application
	 * @param key the key whose association to observer
	 * @param listener the callback method. A weak reference to the method is internally created and stored
	 */
	void addOnConfigurationChangeListener(String key, OnConfigurationChange<?> listener);

	/**
	 * Writes the configuration to file. Should be called before application shutdown
	 * to persist changes. Can be called multiple times to sync to file earlier.
	 * @throws IOException If writing to file fails
	 */
	void syncConfigurations() throws IOException;
}
