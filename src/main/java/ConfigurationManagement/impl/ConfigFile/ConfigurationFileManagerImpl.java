package ConfigurationManagement.impl.ConfigFile;

import ConfigurationManagement.Interfaces.ConfigurationFileManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class ConfigurationFileManagerImpl implements ConfigurationFileManager {

    private Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());
    private Gson gson;
    private File configFile;

    public ConfigurationFileManagerImpl(File configFile, Gson gson) {
        this.gson = gson;
        this.configFile = configFile;
    }

    @Override
    public Map<String, Object> readConfigurations() throws IOException {
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

        Type mapStringObject = new TypeToken<Map<String, Object>>(){}.getType();
        Map<String, Object> configMap = gson.fromJson(configFileString.toString().trim(), mapStringObject);

        return configMap!=null ? configMap : new HashMap<>();
    }

    @Override
    public void writeConfigurations(Map<String, Object> configurations) throws IOException {
        logger.debug("Writing configurations");

        Type mapStringObject = new TypeToken<Map<String, Object>>(){}.getType();
        String jsonConfig = gson.toJson(configurations, mapStringObject);

        createIfNotExist(configFile);
        BufferedWriter bw = new BufferedWriter(new FileWriter(configFile));
        bw.write(jsonConfig);
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
