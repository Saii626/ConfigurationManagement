package app.saikat.ConfigurationManagement;

public class MissingConfigurationValue extends Exception{

	private static final long serialVersionUID = -2986964512729608414L;

	public MissingConfigurationValue(String key) {
		super(String.format("%s configuration missing", key));
	}
}
