package ConfigurationManagement;

import dagger.Component;

@ConfigurationScope
@Component(modules = ConfigurationManagerModule.class)
public interface ConfigurationManagerComponent {

    ConfigurationManager getConfigurationManager();

}
