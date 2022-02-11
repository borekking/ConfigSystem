package de.borekking.configSystem;

import de.borekking.configSystem.util.JavaUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Config {

    // Word Separator: all get, set and contain keys will be split at it.
    public static final String WORD_SEPARATOR = "\\.";

    // Values
    private final Map<String, Object> values;

    // Default values
    private final Config defaultValues;

    public Config() {
        this.values = new HashMap<>();
        this.defaultValues = new Config(false);
    }

    // Constructor for creating a Config without defaultValues
    private Config(boolean defaultValues) {
        this.values = new HashMap<>();

        if (defaultValues) this.defaultValues = new Config(false);
        else this.defaultValues = null;
    }

    // ------------<Default values>------------
    public void setDefault(String key, Object def) {
        if (this.defaultValues == null) return;

        this.defaultValues.set(key, def);
    }

    public Object getDefault(String key) {
        return this.defaultValues == null ? null : this.defaultValues.get(key);
    }

    public boolean containDefault(String key) {
        return this.defaultValues != null && this.defaultValues.containDefault(key);
    }
    // ------------<Default values>------------

    // ------------<Setting Values>------------
    public void set(String key, Object object) {
        String[] segments = key.split(WORD_SEPARATOR);

        // Make sure it's not an empty key
        if (segments.length == 0) {
            throw new IllegalArgumentException("You can not have an empty key!");
        }

        // Get/Create inner JSONConfig for second last segment (might be this)
        Config innerConfig = this.createInnerConfigImp(JavaUtils.arrayRemoveEnd(segments, 1), 0);

        // Set object in innerConfig's values
        innerConfig.values.put(segments[segments.length - 1], object);
    }

    // Adding inner configs
    public void setInnerConfig(String key, Config config) {
        this.set(key, config);
    }
    // ------------</Setting Values>------------

    // Checking if a config contains a key
    public boolean contains(String key) {
        String[] segments = key.split(WORD_SEPARATOR);
        return this.containsImp(segments, 0);
    }

    private boolean containsImp(String[] segments, int index) {
        String key = segments[index];

        // Base case when end of segments array is reached
        if (segments.length - 1 == index) {
            return this.values.containsKey(key);
        }

        // Get key's value
        Object value = this.values.get(key);

        // Check if value is a config -> Recursively call containImp
        if (value instanceof Config)
            return ((Config) value).containsImp(segments, index + 1);

        // Otherwise, return false
        return false;
    }

    // ------------<Getting Values>------------
    // Getting a value as List<?>
    public List<?> getList(String key) {
        Object value = this.get(key);

        // Make sure value actually is a List, otherwise return null
        if (!(value instanceof List)) return null;

        return (List<?>) value;
    }

    // Getting a value as List of a specified type using a function
    public <T> List<T> getList(String key, Predicate<Object> tester, Function<Object, T> function) {
        List<?> list = this.getList(key);
        if (list == null) return null;
        //                         No null values
        return list.stream().filter(Objects::nonNull).filter(tester).map(function).collect(Collectors.toList());
    }

    // Getting a value as Object
    public Object get(String key) {
        String[] segments = key.split(WORD_SEPARATOR);
        Object value = this.getImp(segments, 0);

        // If value is null return default value, else return value
        return value == null ? this.getDefault(key) : value;
    }

    // If key is not found, def is returned
    public Object get(String key, Object def) {
        Object value = this.get(key);
        return value == null ? def : value;
    }

    public Config getInnerConfig(String key) {
        Object value = this.get(key);
        return value instanceof Config ? (Config) value : null;
    }

    private Object getImp(String[] segments, int index) {
        if (segments.length == 0) return this;

        // Get current key and associated value
        String key = segments[index];
        Object value = this.values.get(key);

        // Base Case: End of segments array is reached -> Return current value
        if (segments.length - 1 == index) return value;

        // Check if value is a config -> Recursively call getImp
        if (value instanceof Config)
            return ((Config) value).getImp(segments, index + 1);

        // Otherwise, return null
        return null;
    }
    // ------------</Getting Values>------------

    // ------------<Creating inner Config>------------
    public Config createInnerConfig(String key) {
        String[] segments = key.split(WORD_SEPARATOR);
        return this.createInnerConfigImp(segments, 0);
    }

    private Config createInnerConfigImp(String[] segments, int index) {
        // If last key is reached, return current config, this
        if (segments.length == index) return this;

        // Get and associated value
        String key = segments[index];
        Object value = this.values.get(key);

        // If value already is a JSONConfig just call createInnerConfigImp on it
        if (value instanceof Config)
            return ((Config) value).createInnerConfigImp(segments, index + 1);

        // Create innerConfig and eventual overwrite current entry
        Config innerConfig = new Config();
        this.values.put(key, innerConfig);

        // Recursively call createInnerConfigImp innerConfig which next segment
        return innerConfig.createInnerConfigImp(segments, index + 1);
    }
    // ------------</Creating inner Config>------------

    // ------------<Saving>------------
    public void save(File file) throws IOException {
        new ConfigWriter(this).write(file);
    }
    // ------------</Saving>------------

    // ------------<KeySet>------------
    public List<String> getKeySet() {
        List<String> keys = new ArrayList<>(this.values.keySet());
        if (this.defaultValues != null)
            keys.addAll(this.defaultValues.getKeySet());
        return keys;
    }
    // ------------</KeySet>------------

    // ------------<Object Methods>------------
    @Override
    public String toString() {
        return "JSONConfig{" +
                "values=" + values +
                '}';
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) return true;

        if (!(that instanceof Config)) return false;

        Config config = (Config) that;
        return Objects.equals(this.values, config.values);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.values);
    }

    // ------------</Object Methods>------------
}
