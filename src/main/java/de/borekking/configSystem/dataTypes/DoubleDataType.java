package de.borekking.configSystem.dataTypes;

class DoubleDataType implements IDataType<Double> {

    @Override
    public Double convert(Object o) {
        return (double) o;
    }

    @Override
    public boolean test(Object o) {
        return o instanceof Double;
    }

    @Override
    public Double getDef() {
        return 0D;
    }
}
