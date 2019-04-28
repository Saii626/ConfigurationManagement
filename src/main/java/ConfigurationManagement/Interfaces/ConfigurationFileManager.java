package ConfigurationManagement.Interfaces;


import java.io.IOException;

public interface ConfigurationFileManager {

    Configurations readConfigurations() throws IOException;

    void writeConfigurations(Configurations configurations) throws IOException;
}
