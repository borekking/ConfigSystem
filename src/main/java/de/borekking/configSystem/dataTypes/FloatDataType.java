package de.borekking.configSystem.dataTypes;

class FloatDataType implements IDataType<Float> {

    @Override
    public Float convert(Object o) {
        return (float) o;
    }

    @Override
    public boolean test(Object o) {
        return o instanceof Float;
    }

    @Override
    public Float getDef() {
        return 0F;
    }
}
