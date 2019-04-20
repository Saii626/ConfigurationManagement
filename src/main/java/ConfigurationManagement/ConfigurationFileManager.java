package ConfigurationManagement;

import java.io.IOException;
import java.util.Map;

public interface ConfigurationFileManager {

    Map<ConfigKey, Object> getConfigurations() throws IOException;

    void sync() throws IOException;
}
