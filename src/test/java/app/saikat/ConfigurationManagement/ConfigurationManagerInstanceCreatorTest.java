package app.saikat.ConfigurationManagement;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                .setPrettyPrinting()
                .create();

        ConfigurationManager configurationManager = ConfigurationManagerInstanceCreator.createInstance(new File(System.getProperty("user.home")+"/test"), gson);

        configurationManager.put("name", "saikat");
        configurationManager.put("age", 10);

        configurationManager.addOnConfigurationChangeListener("name", (OnConfigurationChange<String>) (oldV, newV) -> {
            logger.debug("Config {} changing from {} to {}", "name", oldV, newV);
        });

        configurationManager.put("name", "Saikat");
        configurationManager.syncConfigurations();

        ConfigurationManager configManager = ConfigurationManagerInstanceCreator.createInstance(new File(System.getProperty("user.home")+"/test"), gson);
        assertEquals("Wrong name", configManager.<String>getRaw("name"), "Saikat");
        assertEquals("Wrong age", (int) configManager.<Integer>getRaw("age"), 10);
    }
}