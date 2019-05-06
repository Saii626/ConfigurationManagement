package app.saikat.ConfigurationManagement;

public class MissingConfigurationValue extends Exception{

    public MissingConfigurationValue(String  key){
        super(String.format("%s configuration missing", key));
    }
}
