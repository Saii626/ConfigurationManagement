package ConfigurationManagement;

public interface OnConfigurationChange<T> {

    void onConfigurationChange(T oldVal, T newVal);
}
