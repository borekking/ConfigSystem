package de.borekking.configSystem.dataTypes;

public class NumberDataType implements IDataType<Number> {

    @Override
    public Number convert(Object o) {
        return (Number) o;
    }

    @Override
    public boolean test(Object o) {
        return o instanceof Number;
    }

    @Override
    public Number getDef() {
        return 0;
    }
}
