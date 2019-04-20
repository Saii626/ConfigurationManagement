package ConfigurationManagement;

public class MissingConfigurationValue extends Exception{

    public MissingConfigurationValue(ConfigKey key){
        super(String.format("%s : %s configuration missing", key.getKey(), key.getSerializer().getSimpleName()));
    }
}
