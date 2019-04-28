package ConfigurationManagement.impl.ConfigFile;

import ConfigurationManagement.ConfigurationScope;
import ConfigurationManagement.Interfaces.ConfigurationFileManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dagger.Module;
import dagger.Provides;

import java.io.Reader;
import java.io.Writer;

@Module
public class ConfigFileModule {

    @Provides
    @ConfigurationScope
    static ConfigurationFileManager getFileManager(Gson gson, Reader reader, Writer writer) {
        return new ConfigurationFileManagerImpl(gson, reader, writer);
    }

    @Provides
    @ConfigurationScope
    static Gson getConfigGson() {
        return new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                .setPrettyPrinting()
                .registerTypeAdapter(ConfigurationsImpl.class, new ConfigurationsImpl.ConfigurationsAdapter())
                .create();
    }
}
