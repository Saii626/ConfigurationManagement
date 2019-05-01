package ConfigurationManagement.Interfaces;


import java.io.IOException;


/**
 * Internal class. Should not be used outside the module directly
 */
public interface ConfigurationFileManager {

    Configurations readConfigurations() throws IOException;

    void writeConfigurations(Configurations configurations) throws IOException;
}
