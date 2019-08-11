package app.saikat.ConfigurationManagement.impl.ConfigFile;

import java.io.File;

import com.google.gson.Gson;

import app.saikat.ConfigurationManagement.interfaces.ConfigFile;
import app.saikat.ConfigurationManagement.interfaces.ConfigurationFileManager;
import app.saikat.DIManagement.Provides;

class ConfigFileMgrProvider {

    @Provides
    static ConfigurationFileManager getFileManager(Gson gson, @ConfigFile File file) {
        return new ConfigurationFileManagerImpl(file, gson);
    }
}