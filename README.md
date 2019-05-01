# ConfigurationManagement
Submodule for managing configurations

### How to use:
1. Build the project
    ```bash
    $  gradle java
    ...
    ...
    BUILD SUCCESSFUL
    ```

2. Create **ConfigurationManager** instance
    ```
    ConfigurationManager configurationManager = ConfigurationManagerInstanceCreator
                                                .createInstance(new File("configFile"));
    ```
