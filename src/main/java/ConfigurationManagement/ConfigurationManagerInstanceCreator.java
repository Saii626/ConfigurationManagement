package ConfigurationManagement;

import ConfigurationManagement.Interfaces.ConfigurationFileManager;
import ConfigurationManagement.Interfaces.ConfigurationManager;
import ConfigurationManagement.impl.ConfigFile.ConfigurationFileManagerImpl;
import ConfigurationManagement.impl.ConfigFile.ConfigurationsImpl;
import ConfigurationManagement.impl.ConfigManager.ConfigurationManagerImpl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;

public class ConfigurationManagerInstanceCreator {

    public static ConfigurationManager createInstance(File file) throws IOException {
        return new ConfigurationManagerImpl(createConfigurationFileManager(file));
    }

    private static ConfigurationFileManager createConfigurationFileManager(File file) {
        return new ConfigurationFileManagerImpl(createGson(), file);
    }

    private static Gson createGson() {
        return new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                .setPrettyPrinting()
                .registerTypeAdapter(ConfigurationsImpl.class, new ConfigurationsImpl.ConfigurationsAdapter())
                .create();
    }
}
