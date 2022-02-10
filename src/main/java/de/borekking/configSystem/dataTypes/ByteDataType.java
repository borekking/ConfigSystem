package de.borekking.configSystem.dataTypes;

class ByteDataType implements IDataType<Byte> {

    @Override
    public Byte convert(Object o) {
        return (byte) o;
    }

    @Override
    public boolean test(Object o) {
        return o instanceof Byte;
    }

    @Override
    public Byte getDef() {
        return 0;
    }
}
