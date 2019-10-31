package app.saikat.ConfigurationManagement.impl.ConfigManager;

import java.io.IOException;

import app.saikat.ConfigurationManagement.interfaces.ConfigurationFileManager;
import app.saikat.ConfigurationManagement.interfaces.ConfigurationManager;
import app.saikat.DIManagement.Provides;

class ConfigMgrProvider {

	@Provides
	static ConfigurationManager getConfigurationManager(ConfigurationFileManager fileManager) throws IOException {
		return new ConfigurationManagerImpl(fileManager);
	}
}