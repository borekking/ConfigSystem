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

    public Config() {
        this.values = new HashMap<>();
    }

    // Constructor for getting Config from given values in HashMap
    public Config(Map<String, Object> values) {
        this();
        this.values.putAll(values);
    }

    // ------------<Default values>------------
    public void setDefault(String key, Object def) {
        // Add value only if it is not contained yet.
        if (this.contains(key)) return;

        this.set(key, def);
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
        return this.getImp(segments, 0);
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
        return new ArrayList<>(this.values.keySet());
    }
    // ------------</KeySet>------------

    // ------------<Object Methods>------------
    @Override
    public String toString() {
        return '{' +
                values.toString() +
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
