package ConfigurationManagement;

import ConfigurationManagement.Interfaces.ConfigurationManager;
import ConfigurationManagement.Interfaces.OnConfigurationChange;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

public class ConfigurationManagerInstanceCreatorTest {

    private Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    @Test
    public void createConfigurationManager() throws IOException {
        ConfigurationManager configurationManager = ConfigurationManagerInstanceCreator.createInstance(new File("~/test"));

        configurationManager.put("name", "saikat");
        configurationManager.put("age", 10);

        configurationManager.addOnConfigurationChangeListener("name", (OnConfigurationChange<String>) (oldV, newV) -> {
            logger.debug("Config {} changing from {} to {}", "name", oldV, newV);
        });

        configurationManager.put("name", "Saikat");
        configurationManager.syncConfigurations();

        ConfigurationManager configManager = ConfigurationManagerInstanceCreator.createInstance(new File("~/test"));
        assertEquals("Wrong name", configManager.<String>getRaw("name"), "Saikat");
        assertEquals("Wrong age", configManager.<Integer>getRaw("age").intValue(), 10);
    }
}