package de.borekking.configSystem;

import de.borekking.configSystem.dataTypes.IDataType;

import java.util.List;

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
    }

    // Getting specified datatype suing IDataTypes
    public  <T> T get(String key, IDataType<T> type) {
        Object def = this.getDefault(key);
        T defInt = this.getAsT(def, type.getDef(), type);
        return this.get(key, defInt, type);
    }

    public <T> T get(String key, T def, IDataType<T> type) {
        Object value = this.get(key);
        return this.getAsT(value, def, type);
    }

    public <T> List<T> getList(String key, IDataType<T> type) {
        return this.getList(key, type::test , type::convert);
    }

    private  <T> T getAsT(Object o, T def, IDataType<T> type) {
        if (!type.test(o)) return def;
        return type.convert(o);
    }
}
