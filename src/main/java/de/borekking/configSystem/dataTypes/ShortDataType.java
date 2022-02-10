package de.borekking.configSystem.dataTypes;

class ShortDataType implements IDataType<Short> {

    @Override
    public Short convert(Object o) {
        return (short) o;
    }

    @Override
    public boolean test(Object o) {
        return o instanceof Short;
    }

    @Override
    public Short getDef() {
        return (short) 0;
    }
}
