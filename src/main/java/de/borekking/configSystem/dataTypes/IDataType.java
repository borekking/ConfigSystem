package de.borekking.configSystem.dataTypes;

public interface IDataType<T> {

    T convert(Object o);

    // Test if an object is of this type
    boolean test(Object o);

    // Get types default value
    T getDef();
}
