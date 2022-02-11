package de.borekking.configSystem;

import de.borekking.configSystem.dataTypes.IDataType;

import java.util.List;
import java.util.Map;

public class DefiningConfig extends Config {

    /*
     * Config with ability to get values of specific primitive types.
     * These types are given as IDataType which you can from DataTypes class.
     *
     * Implemented:
     *   - boolean
     *   - byte
     *   - char
     *   - short
     *   - int
     *   - long
     *   - float
     *   - double
     *   - String
     *   - Number
     *
     */

    public DefiningConfig() {
        super();
    }

    // Constructor for getting DefiningConfig from given values in HashMap
    public DefiningConfig(Map<String, Object> values) {
        super(values);
    }

    // Overwritten methods related to getting inner DefiningConfigs
    @Override
    public DefiningConfig getInnerConfig(String key) {
        Object value = this.get(key);
        return value instanceof DefiningConfig ? (DefiningConfig) value : null;
    }

    @Override
    public DefiningConfig createInnerConfig(String key) {
        String[] segments = key.split(Config.WORD_SEPARATOR);
        return this.createInnerConfigImp(segments, 0);
    }

    private DefiningConfig createInnerConfigImp(String[] segments, int index) {
        // If last key is reached, return current config, this
        if (segments.length == index) return this;

        // Get and associated value
        String key = segments[index];
        Object value = this.get(key);

        // If value already is a JSONConfig just call createInnerConfigImp on it
        if (value instanceof DefiningConfig)
            return ((DefiningConfig) value).createInnerConfigImp(segments, index + 1);

        // Create innerConfig and eventual overwrite current entry
        DefiningConfig innerConfig = new DefiningConfig();
        this.set(key, innerConfig);

        // Recursively call createInnerConfigImp innerConfig which next segment
        return innerConfig.createInnerConfigImp(segments, index + 1);
    }

    // Getting specified datatype suing IDataTypes
    public <T> T get(String key, IDataType<T> type) {
        Object def = this.getDefault(key);
        T defInt = this.getAsT(def, type.getDef(), type);
        return this.get(key, defInt, type);
    }

    public <T> T get(String key, T def, IDataType<T> type) {
        Object value = this.get(key);
        return this.getAsT(value, def, type);
    }

    public <T> List<T> getList(String key, IDataType<T> type) {
        return this.getList(key, type::test, type::convert);
    }

    private <T> T getAsT(Object o, T def, IDataType<T> type) {
        if (!type.test(o)) return def;
        return type.convert(o);
    }
}
