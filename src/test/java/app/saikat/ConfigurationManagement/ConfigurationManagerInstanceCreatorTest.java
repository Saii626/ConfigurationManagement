package app.saikat.ConfigurationManagement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import app.saikat.ConfigurationManagement.interfaces.ConfigurationManager;
import app.saikat.ConfigurationManagement.interfaces.OnConfigurationChange;
import app.saikat.DIManagement.Interfaces.DIManager;

import java.io.IOException;

import static org.junit.Assert.*;

public class ConfigurationManagerInstanceCreatorTest {

	 private Logger logger = LogManager.getLogger(this.getClass().getSimpleName());

	 @Test
	 public void createConfigurationManager() throws IOException {
		 DIManager manager = DIManager.newInstance();

		 manager.initialize("app.saikat");		 

		 ConfigurationManager configurationManager = manager.getBeansOfType(ConfigurationManager.class).iterator().next().getProvider().get();

		 configurationManager.put("name", "saikat");
		 configurationManager.put("age", 10);

		 configurationManager.addOnConfigurationChangeListener("name", (OnConfigurationChange<String>) (oldV, newV) -> {
			 logger.debug("Config {} changing from {} to {}", "name", oldV, newV);
		 });

		 configurationManager.put("name", "Saikat");
		 configurationManager.syncConfigurations();

		 assertEquals("Wrong name", configurationManager.<String>getRaw("name"), "Saikat");
		 assertEquals("Wrong age", (int) configurationManager.<Integer>getRaw("age"), 10);
	 }
}