package ConfigurationManagement.impl.ConfigFile;

import ConfigurationManagement.Interfaces.ConfigurationFileManager;
import ConfigurationManagement.Interfaces.Configurations;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ConfigurationFileManagerImpl implements ConfigurationFileManager {

    private Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());
    private Gson gson;
    private File configFile;

    public ConfigurationFileManagerImpl(Gson gson, File configFile) {
        this.gson = gson;
        this.configFile = configFile;
    }

    @Override
    public Configurations readConfigurations() throws IOException {
        logger.debug("Reading configurations");

        createIfNotExist(configFile);
        BufferedReader br = new BufferedReader(new FileReader(configFile));
        StringBuilder configFileString = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {

            configFileString.append(line);
            configFileString.append('\n');
        }
        br.close();

        Configurations configurations = gson.fromJson(configFileString.toString().trim(), ConfigurationsImpl.class);
        return configurations != null ? configurations : new ConfigurationsImpl();
    }

    @Override
    public void writeConfigurations(Configurations configurations) throws IOException {
        logger.debug("Writing configurations");
        String jsonConfig = gson.toJson(configurations, ConfigurationsImpl.class);

        createIfNotExist(configFile);
        BufferedWriter bw = new BufferedWriter(new FileWriter(configFile));
        bw.write(jsonConfig);
        bw.write('\n');
        bw.flush();
        bw.close();
    }

    private void createIfNotExist(File file) throws IOException {
        if (!file.exists()) {
            if (file.getParentFile() != null && !file.getParentFile().exists()) {
                file.getParentFile().mkdir();
            }

            file.createNewFile();
        }
    }
}
