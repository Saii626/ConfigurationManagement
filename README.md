# ConfigurationManagement
Submodule for managing configurations

### How to use:
1. Build the project
    ```bash
    $  gradle java
    
    BUILD SUCCESSFUL in 5s
    ```

2. Create a **ConfigurationManagerComponent**
    ```
        ConfigurationManagerComponent configurationManagerComponent = DaggerConfigurationManagerComponent.builder()
                .unsatisfiedConfigurationDependenciesModule(new UnsatisfiedConfigurationDependenciesModule(
                    file  // File location of config file
                 ))
                .build();
    ```
    
3. Get **ConfigurationManager** instance from the component
    ```
       ConfigurationManager configurationManager = configurationManagerComponent.getConfigurationManager();
    ```
