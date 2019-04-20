package ConfigurationManagement.impl.ConfigFile;

import ConfigurationManagement.ConfigurationFileManager;
import ConfigurationManagement.ConfigurationScope;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dagger.Module;
import dagger.Provides;

import java.io.File;
import java.util.Optional;

@Module
public class ConfigFileModule {

    @Provides
    @ConfigurationScope
    static ConfigurationFileManager getFileManager(Optional<File> file, Gson gson) {
        return new ConfigurationFileManagerImpl(file, gson);
    }

    @Provides
    @ConfigurationScope
    static Gson getConfigGson() {
        return new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                .setPrettyPrinting()
                .registerTypeAdapter(ConfigJsonFile.class, new ConfigJsonFileSerializerDeserializer())
                .create();
    }
}
