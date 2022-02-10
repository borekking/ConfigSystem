package de.borekking.configSystem;

import de.borekking.configSystem.files.FileLoader;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class ConfigWriter {

    /*
     * Package-protected class for writing a config into a file using JSON-Format.
     *
     */

    private final Config config;

    // Constructor taking the JSONConfig
    ConfigWriter(Config config) {
        this.config = config;
    }

    void write(File file) throws IOException {
        // Check file ending with ".json" to be valid
        if (!file.getName().endsWith(".json")) {
            throw new IllegalArgumentException("File is exposed to be a json-file but does not end with \".json\": " + file.getName());
        }

        // Make sure file is loaded
        FileLoader fileLoader = new FileLoader(file, false);
        fileLoader.load();

        // Create JSONObject
        JSONObject jsonObject = this.createJSONObject(this.config);

        // Write JSONObject into File
        this.writeJSONObject(jsonObject, file);
    }

    // Actually write a JSONObject into a file
    private void writeJSONObject(JSONObject jsonObject, File file) throws IOException {
        FileWriter writer = new FileWriter(file);
        writer.write(jsonObject.toJSONString());
        writer.flush();
    }

    // Methode creating a JSONObject for a JSONConfig recursively by calling
    // the methode for every inner JSONConfig
    private JSONObject createJSONObject(Config config) {
        JSONObject main = new JSONObject(); // Main JSONObject holding all the inner data

        List<String> keys = config.getKeySet(); // All keys of current
        for (String key : keys) {
            Object value = config.get(key);

            // 1. Case: value is an inner config
            if (value instanceof Config) {
                // Recursively use this methode
                main.put(key, this.createJSONObject((Config) value));

            } else if (value instanceof List) { // 2. Case: Value is a List
                // Get value as List and convert it to JSONArray
                List<?> list = config.getList(key);
                JSONArray array = new JSONArray();
                array.addAll(list);

                // Put JSON Array into main JSONObject
                main.put(key, array);
            } else { // Default case: value is just a normal object
                main.put(key, value);
            }
        }

        return main;
    }
}
