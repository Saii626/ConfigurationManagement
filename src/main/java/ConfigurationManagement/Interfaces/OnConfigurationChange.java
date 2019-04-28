package ConfigurationManagement.Interfaces;

public interface OnConfigurationChange<T> {

    /**
     * Called when configuration value changes
     * @param oldVal the previous value associated with key
     * @param newVal the new value to be associated with the key
     */
    void onConfigurationChange(T oldVal, T newVal);
}
