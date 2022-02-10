package de.borekking.configSystem;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ConfigLoader {

    /*
     * Class for creating a Config given File with json formatter.
     *
     */

    // Static Function to get DefiningConfig from File
    public static DefiningConfig loadDefiningConfig(File file) throws IOException, ParseException {
        return new ConfigLoader(file).createConfig();
    }

    private final File file;

    private ConfigLoader(File file) {
        this.file = file;
    }

    // Create a DefiningConfig based on File
    private DefiningConfig createConfig() throws IOException, ParseException {
        JSONObject inputConfig = (JSONObject) new JSONParser().parse(new FileReader(this.file));

        return this.readConfig(inputConfig);
    }

    private DefiningConfig readConfig(JSONObject jsonConfig) {
        DefiningConfig config = new DefiningConfig();

        // Go through jsonConfig's keys
        for (String key : (Set<String>) jsonConfig.keySet()) {
            Object value = jsonConfig.get(key);

            // 1. Case: value is an inner config
            if (value instanceof JSONObject) {
                // Recursively use this methode
                config.set(key, this.readConfig((JSONObject) value));

            } else if (value instanceof JSONArray) { // 2. Case: value is a (json) array
                // Get value as JSONArray and convert it to List
                JSONArray array = (JSONArray) value;
                List<Object> list = new ArrayList<>(array);

                // Put List in config
                config.set(key, list);
            } else { // Default case: value is just a normal oObject
                config.set(key, value);
            }
        }

        return config;
    }
}
