package de.borekking.configSystem.dataTypes;

class BooleanDataType implements IDataType<Boolean> {

    @Override
    public Boolean convert(Object o) {
        return (boolean) o;
    }

    @Override
    public boolean test(Object o) {
        return o instanceof Boolean;
    }

    @Override
    public Boolean getDef() {
        return false;
    }
}
