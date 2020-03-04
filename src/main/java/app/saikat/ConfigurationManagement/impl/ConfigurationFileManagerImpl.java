package app.saikat.ConfigurationManagement.impl;

import app.saikat.GsonManagement.JsonObject;
import app.saikat.Annotations.ConfigurationManagement.ConfigFile;
import app.saikat.Annotations.DIManagement.Provides;
import app.saikat.ConfigurationManagement.interfaces.ConfigurationFileManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

class ConfigurationFileManagerImpl implements ConfigurationFileManager {

	private Logger logger = LogManager.getLogger(this.getClass()
			.getSimpleName());
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

		return convertToObjectMap(configFileString.toString()
				.trim());
	}

	@Override
	public void writeConfigurations(Map<String, Object> configurations) throws IOException {
		logger.debug("Writing configurations");

		Map<String, JsonObject> jsonMap = convertToJsonMap(configurations);
		Type mapStringObject = new TypeToken<HashMap<String, JsonObject>>() {}.getType();
		String jsonConfig = gson.toJson(jsonMap, mapStringObject);

		createIfNotExist(configFile);
		BufferedWriter bw = new BufferedWriter(new FileWriter(configFile));
		bw.write(jsonConfig);
		bw.flush();
		bw.close();
	}

	private void createIfNotExist(File file) throws IOException {
		if (!file.exists()) {
			if (file.getParentFile() != null && !file.getParentFile()
					.exists()) {
				file.getParentFile()
						.mkdir();
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

	private Map<String, Object> convertToObjectMap(String data) {

		Type unparsedConfType = new TypeToken<HashMap<String, String>>() {}.getType();
		Map<String, String> unparsedConfig = gson.fromJson(data, unparsedConfType);

		Map<String, Object> parsedConfig = new HashMap<>();

		if (unparsedConfig != null) {
			for (Map.Entry<String, String> entry : unparsedConfig.entrySet()) {
				try {
					JsonObject jsonObject = gson.fromJson(entry.getValue(), JsonObject.class);
					parsedConfig.put(entry.getKey(), jsonObject != null ? jsonObject.getObject() : null);
				} catch (Exception e) {
					logger.error("Error parsing configuration with key: {}, val: {}", entry.getKey(), entry.getValue());
					logger.error("Error: ", e);
				}
			}
		}
		return parsedConfig;
	}

	@Provides
	static ConfigurationFileManager getFileManager(Gson gson, @ConfigFile File file) {
		return new ConfigurationFileManagerImpl(file, gson);
	}
}
