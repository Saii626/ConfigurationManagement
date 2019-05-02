package ConfigurationManagement.Interfaces;


import java.io.IOException;
import java.util.Map;


/**
 * Internal class. Should not be used outside the module directly
 */
public interface ConfigurationFileManager {

    Map<String, Object> readConfigurations() throws IOException;

    void writeConfigurations(Map<String, Object> configurations) throws IOException;
}
