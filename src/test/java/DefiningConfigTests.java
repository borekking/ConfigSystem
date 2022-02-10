import de.borekking.configSystem.DefiningConfig;

import de.borekking.configSystem.dataTypes.DataTypes;
import de.borekking.configSystem.dataTypes.IDataType;

import org.junit.Assert;
import org.junit.Test;

public class DefiningConfigTests {

    @Test
    public void testBoolean() {
        this.testDataType(DataTypes.BOOLEAN, true);
    }

    @Test
    public void testByte() {
        this.testDataType(DataTypes.BYTE, (byte) 42);
    }

    @Test
    public void testChar() {
        this.testDataType(DataTypes.CHAR, (char) 69);
    }

    @Test
    public void testShort() {
        this.testDataType(DataTypes.SHORT, (short) 420);
    }

    @Test
    public void testInt() {
        this.testDataType(DataTypes.INTEGER, 187);
    }

    @Test
    public void testLong() {
        this.testDataType(DataTypes.LONG, 420694206942069L);
    }

    @Test
    public void testFloat() {
        this.testDataType(DataTypes.FLOAT, 0.69F);
    }

    @Test
    public void testDouble() {
        this.testDataType(DataTypes.DOUBLE, 0.420694206942069D);
    }

    @Test
    public void testString() {
        this.testDataType(DataTypes.STRING, "hellow word");
    }

    private <T> void testDataType(IDataType<T> type, T t) {
        String key = "test";

        DefiningConfig config = new DefiningConfig();
        config.set(key, t);

        T value = config.get(key, type);
        Assert.assertEquals(t, value);
    }
}
