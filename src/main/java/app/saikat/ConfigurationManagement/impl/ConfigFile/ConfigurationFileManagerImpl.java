package app.saikat.ConfigurationManagement.impl.ConfigFile;

import app.saikat.ConfigurationManagement.Gson.JsonObject;
import app.saikat.ConfigurationManagement.interfaces.ConfigurationFileManager;
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

        Type mapStringObject = new TypeToken<HashMap<String, JsonObject>>(){}.getType();
        Map<String, JsonObject> configMap = gson.fromJson(configFileString.toString().trim(), mapStringObject);

        return convertToObjectMap(configMap);
    }

    @Override
    public void writeConfigurations(Map<String, Object> configurations) throws IOException {
        logger.debug("Writing configurations");

        Map<String, JsonObject> jsonMap = convertToJsonMap(configurations);
        Type mapStringObject = new TypeToken<HashMap<String, JsonObject>>(){}.getType();
        String jsonConfig = gson.toJson(jsonMap, mapStringObject);

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

    private Map<String, JsonObject> convertToJsonMap(Map<String, Object> map) {
        Map<String, JsonObject> ret = new HashMap<>();

        if (map != null && map.size() > 0) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                ret.put(entry.getKey(), new JsonObject(entry.getValue()));
            }
        }
        return ret;
    }

    private Map<String, Object> convertToObjectMap(Map<String, JsonObject> map) {
        Map<String, Object> ret = new HashMap<>();

        if (map != null && map.size() > 0) {
            for (Map.Entry<String, JsonObject> entry : map.entrySet()) {
                ret.put(entry.getKey(), entry.getValue().getObject());
            }
        }
        return ret;
    }
}
