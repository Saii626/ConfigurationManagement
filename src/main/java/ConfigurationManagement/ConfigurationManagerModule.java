package ConfigurationManagement;

import dagger.Module;
import ConfigurationManagement.impl.ConfigFile.ConfigFileModule;
import ConfigurationManagement.impl.ConfigManager.ConfigManagerModule;

@Module(includes = {ConfigFileModule.class, ConfigManagerModule.class, ConfigurationDependenciesModule.class})
public class ConfigurationManagerModule {

}
