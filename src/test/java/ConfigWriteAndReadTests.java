import de.borekking.configSystem.ConfigLoader;
import de.borekking.configSystem.DefiningConfig;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.parser.ParseException;
import org.junit.Assert;
import org.junit.Test;

public class ConfigWriteAndReadTests {

    /*
     * Not that integer values will always be set as long,
     * and floating point values will be set as double (due to JSON).
     *
     * This is why I only test long-, double-, and String values here.
     *
     */

    private final List<File> files = new ArrayList<>();

    @Test
    public void testLong() throws IOException, ParseException {
        this.testDefault("test1.json", 20L);
    }

    @Test
    public void testDouble() throws IOException, ParseException {
        this.testDefault("test2.json", 30.43D);
    }

    @Test
    public void testString() throws IOException, ParseException {
        this.testDefault("test3.json", "HELLO WORD");
    }

    private <T> void testDefault(String fileName, T value) throws IOException, ParseException {
        DefiningConfig config = new DefiningConfig();
        config.set("key", value);

        File file = this.newFile(fileName);
        config.save(file);

        DefiningConfig config1 = ConfigLoader.loadDefiningConfig(file);

        Assert.assertEquals(config, config1);
    }

    @Test
    public void testLongInnerConfigs() throws IOException, ParseException {
        this.testWithInnerConfig("test4.json", 20L);
    }

    @Test
    public void testDoubleInnerConfig() throws IOException, ParseException {
        this.testWithInnerConfig("test5.json", 20.3289D);
    }

    @Test
    public void testStringInnerConfigs() throws IOException, ParseException {
        this.testWithInnerConfig("test6.json", 20L);
    }

    private <T> void testWithInnerConfig(String fileName, T value) throws IOException, ParseException {
        DefiningConfig innerConfig = new DefiningConfig();
        innerConfig.set("key0", value);

        DefiningConfig config = new DefiningConfig();
        config.set("key1", innerConfig);

        File file = this.newFile(fileName);
        config.save(file);

        DefiningConfig config1 = ConfigLoader.loadDefiningConfig(file);

        Assert.assertEquals(config, config1);
    }

    private File newFile(String name) {
        File file = new File(name);

        this.files.add(file);

        return file;
    }
}
