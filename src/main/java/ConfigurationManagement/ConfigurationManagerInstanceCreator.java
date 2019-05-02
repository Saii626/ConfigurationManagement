package ConfigurationManagement;

import ConfigurationManagement.Interfaces.ConfigurationManager;
import ConfigurationManagement.impl.ConfigFile.ConfigurationFileManagerImpl;
import ConfigurationManagement.impl.ConfigManager.ConfigurationManagerImpl;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;

public class ConfigurationManagerInstanceCreator {

    public static ConfigurationManager createInstance(File file, Gson gson) throws IOException {
        return new ConfigurationManagerImpl(new ConfigurationFileManagerImpl(file, gson));
    }
}
