package ConfigurationManagement.Interfaces;

/**
 * Internal interface. Shouldnot be used directly
 */
public interface Configurations {

    <T> T get(String key);

    <T> void put(String key, T value);

    void delete(String key);

    boolean containsKey(String key);
}
