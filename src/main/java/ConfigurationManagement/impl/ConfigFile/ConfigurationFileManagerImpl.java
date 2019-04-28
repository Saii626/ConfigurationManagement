package ConfigurationManagement.impl.ConfigFile;

import ConfigurationManagement.Interfaces.ConfigurationFileManager;
import ConfigurationManagement.Interfaces.Configurations;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

public class ConfigurationFileManagerImpl implements ConfigurationFileManager {

    private Reader reader;
    private Writer writer;

    private Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());
    private Gson gson;

    public ConfigurationFileManagerImpl(Gson gson, Reader reader, Writer writer) {
        this.gson = gson;
        this.reader = reader;
        this.writer = writer;
    }

    @Override
    public Configurations readConfigurations() throws IOException {
        logger.debug("Reading configurations");
        BufferedReader br = new BufferedReader(reader);

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

        BufferedWriter bw = new BufferedWriter(writer);
        bw.write(jsonConfig);
        bw.write('\n');
        bw.flush();
        bw.close();
    }

}
