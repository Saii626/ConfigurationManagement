package ConfigurationManagement;

import ConfigurationManagement.Interfaces.ConfigurationManager;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class TestFunctionality {
    int i;
    String j;


    public static void main(String[] args) throws IOException {

        File file = new File("Test");
        file.createNewFile();

        ConfigurationManagerComponent configurationManagerComponent = DaggerConfigurationManagerComponent.builder()
                .configurationDependenciesModule(new ConfigurationDependenciesModule(new FileReader(file), new FileWriter(file, true)))
                .build();

        ConfigurationManager configurationManager = configurationManagerComponent.getConfigurationManager();
    }
}
