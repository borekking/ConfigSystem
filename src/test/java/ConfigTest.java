import de.borekking.configSystem.Config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class ConfigTest {

    @Test
    public void testGettingSettingOneLayerInt() {
        this.test("test", 42);
    }

    @Test
    public void testGettingSettingOneLayerString() {
        this.test("test", "Was up");
    }

    @Test
    public void testGettingSettingMultiLayersInt() {
        this.test("test.test2.test3", 42);
    }

    @Test
    public void testGettingSettingMultiLayersString() {
        this.test("test.test1.test2", "Was up");
    }

    @Test
    public void testContainsOneLayer() {
        this.testContaining("test", "TEST");
    }

    @Test
    public void testContainsMultiLayers() {
        this.testContaining("test.hey.wasup.test", 187);
    }

    @Test
    public void testCreatingInnerConfig() {
        String key = "test.key";

        Config config = new Config();
        Config innerConfig = config.createInnerConfig(key);

        Assert.assertEquals(config.get(key), innerConfig);
    }

    @Test
    public void testEquals() {
        Config config1 = new Config(), config2 = new Config();

        config1.set("Test.test", 42);
        config2.set("Test.test", 42);

        Assert.assertEquals(config1, config1);
    }

    @Test
    public void testList1() {
        String key = "test";
        Config config = new Config();

        List<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 2, 3));
        config.set(key, list);

        List<?> value = config.getList(key);
        Assert.assertEquals(list, value);
    }

    @Test
    public void testList2() {
        String key = "test";
        Config config = new Config();

        List<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 2, 3));
        config.set(key, list);

        List<Integer> value = config.getList(key, o -> o instanceof Number, o -> ((Number) o).intValue());
        Assert.assertEquals(list, value);
    }

    @Test
    public void testDefaults() {
        String key = "test";
        Double def = 2.0D;

        Config config = new Config();
        config.setDefault(key, def);

//        Assert.assertEquals(def, config.getDefault(key));
        Assert.assertEquals(def, config.get(key));
    }

    // Removing
    @Test
    public void testRemovingKeyNormalA() {
        Config config = new Config();
        String key = "key";

        config.set(key, 42);
        Assert.assertTrue(config.contains(key));

        boolean success = config.remove(key);
        Assert.assertTrue(success);
        Assert.assertFalse(config.contains(key));
    }

    @Test
    public void testRemovingKeyNormalB() {
        Config config = new Config();
        String key = "key";

        config.set(key, 42);
        Assert.assertTrue(config.contains(key));

        boolean success = config.remove("test123");
        Assert.assertFalse(success);
        Assert.assertTrue(config.contains(key));
    }

    @Test
    public void testRemovingKeySectionsA() {
        Config config = new Config();
        String keyOuter = "key.test", keyInner = keyOuter + ".inner3";

        config.set(keyInner, 42);
        Assert.assertTrue(config.contains(keyOuter));
        Assert.assertTrue(config.contains(keyInner));

        boolean success = config.remove(keyInner);
        Assert.assertTrue(success);
        Assert.assertTrue(config.contains(keyOuter));
        Assert.assertFalse(config.contains(keyInner));
    }

    @Test
    public void testRemovingKeySectionsB() {
        Config config = new Config();
        String keyOuter = "key.test", keyInner = keyOuter + ".inner3";

        config.set(keyInner, 42);
        Assert.assertTrue(config.contains(keyOuter));
        Assert.assertTrue(config.contains(keyInner));

        boolean success = config.remove(keyOuter);
        Assert.assertTrue(success);
        Assert.assertFalse(config.contains(keyOuter));
        Assert.assertFalse(config.contains(keyInner));
    }

    @Test
    public void testRemovingKeyAndValueA() {
        String key = "key";
        Object value = "Hello Word";

        Config config = new Config();
        config.set(key, value);
        Assert.assertTrue(config.contains(key));

        boolean success = config.remove(key, value);
        Assert.assertTrue(success);
        Assert.assertFalse(config.contains(key));
    }

    @Test
    public void testRemovingKeyAndValueB() {
        String key = "key";
        Object value = "Hello Word";

        Config config = new Config();
        config.set(key, value);
        Assert.assertTrue(config.contains(key));

        boolean success = config.remove(key, "value");
        Assert.assertFalse(success);
        Assert.assertTrue(config.contains(key));
    }

    private void testContaining(String key, Object value) {
        Config config = new Config();
        config.set(key, value);

        Assert.assertTrue(config.contains(key));
    }

    private void test(String key, Object value) {
        Config config = new Config();
        config.set(key, value);
        Assert.assertEquals(value, config.get(key));
    }

}
