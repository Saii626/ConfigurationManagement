package app.saikat.ConfigurationManagement;

import java.io.File;

import app.saikat.Annotations.ConfigurationManagement.ConfigFile;
import app.saikat.Annotations.DIManagement.Provides;

public class TestConfigFileProvider {

	@Provides
	@ConfigFile
	static File getConfigFile() {
		File testconfigFile = new File(System.getProperty("user.home")+"/test/config_instance_test.conf");

		 if (testconfigFile.exists()) {
			 testconfigFile.delete();
		 }

		 return testconfigFile;
	}
}