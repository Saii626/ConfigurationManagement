package ConfigurationManagement.impl.ConfigManager;

import ConfigurationManagement.ConfigurationFileManager;
import ConfigurationManagement.ConfigurationManager;
import ConfigurationManagement.ConfigurationScope;
import dagger.Module;
import dagger.Provides;

@Module
public class ConfigManagerModule {

    @Provides
    @ConfigurationScope
    static ConfigurationManager getConfigManager(ConfigurationFileManager configurationFileManager) {
        return new ConfigurationManagerImpl(configurationFileManager);
    }
}
