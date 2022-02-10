package de.borekking.configSystem.dataTypes;

class LongDataType implements IDataType<Long> {

    @Override
    public Long convert(Object o) {
        return (long) o;
    }

    @Override
    public boolean test(Object o) {
        return o instanceof Long;
    }

    @Override
    public Long getDef() {
        return 0L;
    }
}
