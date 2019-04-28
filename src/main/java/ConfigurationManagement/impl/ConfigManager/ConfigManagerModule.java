package ConfigurationManagement.impl.ConfigManager;

import ConfigurationManagement.ConfigurationScope;
import ConfigurationManagement.Interfaces.ConfigurationFileManager;
import ConfigurationManagement.Interfaces.ConfigurationManager;
import dagger.Module;
import dagger.Provides;

import java.io.IOException;

@Module
public class ConfigManagerModule {

    @Provides
    @ConfigurationScope
    static ConfigurationManager getConfigManager(ConfigurationFileManager fileManager) {
        try {
            return new ConfigurationManagerImpl(fileManager);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
