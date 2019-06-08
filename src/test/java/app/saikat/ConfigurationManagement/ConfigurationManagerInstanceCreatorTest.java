package app.saikat.ConfigurationManagement;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import app.saikat.ConfigurationManagement.interfaces.ConfigurationManager;
import app.saikat.ConfigurationManagement.interfaces.OnConfigurationChange;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

public class ConfigurationManagerInstanceCreatorTest {

    private Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    @Test
    public void createConfigurationManager() throws IOException {

        ConfigurationManager configurationManager = ConfigurationManagerInstanceHandler.createInstance(new File(System.getProperty("user.home")+"/test"));

        configurationManager.put("name", "saikat");
        configurationManager.put("age", 10);

        configurationManager.addOnConfigurationChangeListener("name", (OnConfigurationChange<String>) (oldV, newV) -> {
            logger.debug("Config {} changing from {} to {}", "name", oldV, newV);
        });

        configurationManager.put("name", "Saikat");
        configurationManager.syncConfigurations();

        ConfigurationManager configManager = ConfigurationManagerInstanceHandler.createInstance(new File(System.getProperty("user.home")+"/test"));
        assertEquals("Wrong name", configManager.<String>getRaw("name"), "Saikat");
        assertEquals("Wrong age", (int) configManager.<Integer>getRaw("age"), 10);
    }
}