package ConfigurationManagement.impl.ConfigFile;

import ConfigurationManagement.ConfigKey;
import ConfigurationManagement.ConfigurationFileManager;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ConfigurationFileManagerImpl implements ConfigurationFileManager {

    private File configFile;
    private Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());
    private Map<ConfigKey, Object> inMemoryConfigurations;
    private Gson gson;

    public ConfigurationFileManagerImpl(Optional<File> file, Gson gson) {
        this.configFile = file.orElseGet(() ->{
            logger.warn("No file provided for persisting configuration. Configurations will be lost on restart");
            return null;
        });
        this.gson = gson;
    }

    @Override
    public Map<ConfigKey, Object> getConfigurations() throws IOException {
        if (configFile == null) {
            logger.warn("Using in-memory configurations");
            inMemoryConfigurations = this.getDefaultConfigurations();
        } else if (!configFile.exists()) {
            logger.debug("Configuration file does not exist. Creating with default values");
            this.createFile();
            inMemoryConfigurations = this.getDefaultConfigurations();
            sync();
        } else {
            logger.debug("Reading and loading config file");
            inMemoryConfigurations = this.readConfigurationFromFile();
        }

        return inMemoryConfigurations;
    }

    @Override
    public void sync() throws IOException {
        if (configFile != null) {
            ConfigJsonFile configJsonFile = new ConfigJsonFile();
            configJsonFile.setConfiguration(inMemoryConfigurations, gson);

            String jsonConfig = gson.toJson(configJsonFile, ConfigJsonFile.class);

            FileWriter fw = new FileWriter(configFile, false);
            fw.write(jsonConfig);
            fw.write('\n');
            fw.close();
        }
    }

    // Private APIs
    private Map<ConfigKey, Object> readConfigurationFromFile() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(configFile));

        logger.debug("Reading from config file");

        StringBuilder configFileString = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {

            configFileString.append(line);
            configFileString.append('\n');
        }
        br.close();

        ConfigJsonFile jsonFile = gson.fromJson(configFileString.toString().trim(), ConfigJsonFile.class);
        return jsonFile.getConfigurations(gson);
    }

    private void createFile() throws IOException {
        if (!configFile.getParentFile().exists()) {
            Files.createDirectory(configFile.toPath().getParent());
        }
        Files.createFile(configFile.toPath());
    }

    private Map<ConfigKey, Object> getDefaultConfigurations() {
        Map<ConfigKey, Object> map = new HashMap<>();

        for (ConfigKey key : ConfigKey.values()) {
            map.put(key, key.getDefaultValue());
        }
        return map;
    }
}
