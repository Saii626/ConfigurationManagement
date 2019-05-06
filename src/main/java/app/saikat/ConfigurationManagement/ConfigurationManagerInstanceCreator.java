package app.saikat.ConfigurationManagement;

import app.saikat.ConfigurationManagement.interfaces.ConfigurationManager;
import app.saikat.ConfigurationManagement.impl.ConfigFile.ConfigurationFileManagerImpl;
import app.saikat.ConfigurationManagement.impl.ConfigManager.ConfigurationManagerImpl;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;

public class ConfigurationManagerInstanceCreator {

    public static ConfigurationManager createInstance(File file, Gson gson) throws IOException {
        return new ConfigurationManagerImpl(new ConfigurationFileManagerImpl(file, gson));
    }
}
