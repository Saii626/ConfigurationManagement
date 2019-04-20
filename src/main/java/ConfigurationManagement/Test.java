package ConfigurationManagement;

import java.io.IOException;

public class Test {

    public static void main(String[] args) throws IOException {
        ConfigurationManager configurationManager = DaggerConfigurationManagerComponent.builder()
                .unsatisfiedConfigurationDependenciesModule(new UnsatisfiedConfigurationDependenciesModule(null))
                .build().getConfigurationManager();
    }
}
