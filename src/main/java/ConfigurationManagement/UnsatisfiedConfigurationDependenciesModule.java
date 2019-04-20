package ConfigurationManagement;

import dagger.Module;
import dagger.Provides;

import java.io.File;
import java.util.Optional;

@Module
public class UnsatisfiedConfigurationDependenciesModule {

    private File configFile;

    public UnsatisfiedConfigurationDependenciesModule(File configFile) {
        this.configFile = configFile;
    }

    @Provides
    @ConfigurationScope
    public Optional<File> getConfigFile() {
        return Optional.ofNullable(configFile);
    }
}
