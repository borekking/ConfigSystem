package de.borekking.configSystem.dataTypes;

public class DataTypes {

    private DataTypes() {
    }

    /*
     * Own Enumeration for all IDataTypes.
     * (Because normal enumerations don't allow Generic.)
     *
     */

    public final static IDataType<Boolean> BOOLEAN = new BooleanDataType();

    public final static IDataType<Byte> BYTE = new ByteDataType();

    public final static IDataType<Character> CHAR = new CharacterDataType();

    public final static IDataType<Short> SHORT = new ShortDataType();

    public final static IDataType<Integer> INTEGER = new IntegerDataType();

    public final static IDataType<Long> Long = new LongDataType();

    public final static IDataType<Float> FLOAT = new FloatDataType();

    public final static IDataType<Double> DOUBLE = new DoubleDataType();

    public final static IDataType<String> STRING = new StringDataType();

}