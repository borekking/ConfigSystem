package de.borekking.configSystem.dataTypes;

class IntegerDataType implements IDataType<Integer> {

    @Override
    public Integer convert(Object o) {
        return (int) o;
    }

    @Override
    public boolean test(Object o) {
        return o instanceof Integer;
    }

    @Override
    public Integer getDef() {
        return 0;
    }
}
