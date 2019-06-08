package app.saikat.ConfigurationManagement;

import app.saikat.ConfigurationManagement.interfaces.ConfigurationManager;
import app.saikat.ConfigurationManagement.Gson.AnnotedExclusionStrategy;
import app.saikat.ConfigurationManagement.Gson.PostProcessingAdapterFactory;
import app.saikat.ConfigurationManagement.impl.ConfigFile.ConfigurationFileManagerImpl;
import app.saikat.ConfigurationManagement.impl.ConfigManager.ConfigurationManagerImpl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;

public class ConfigurationManagerInstanceHandler {

    private static Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            .setExclusionStrategies(new AnnotedExclusionStrategy())
            .registerTypeAdapterFactory(new PostProcessingAdapterFactory())
            .setPrettyPrinting()
            .serializeNulls()
            .create();

    public static ConfigurationManager createInstance(File file) throws IOException {
        return new ConfigurationManagerImpl(new ConfigurationFileManagerImpl(file, gson));
    }

    public static Gson getGson() {
        return gson;
    }
}
