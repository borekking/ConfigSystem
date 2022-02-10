package de.borekking.configSystem.dataTypes;

class StringDataType implements IDataType<String> {

    @Override
    public String convert(Object o) {
        return (String) o;
    }

    @Override
    public boolean test(Object o) {
        return o instanceof String;
    }

    @Override
    public String getDef() {
        return "";
    }
}
