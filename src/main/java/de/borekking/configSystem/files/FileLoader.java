package de.borekking.configSystem.files;

import java.io.File;
import java.io.IOException;

public class FileLoader {

    /*
     * Class for loading a file
     *
     */

    private final File file;

    private final boolean directoryFile;

    public FileLoader(File file, boolean directoryFile) {
        this.file = file;
        this.directoryFile = directoryFile;
    }

    /**
     *
     * @return If the given file exists now (also returns true if file existed before).
     */
    public boolean load() throws IOException {
        if (this.file.exists()) return true;

        // Load parents
        File parent = this.file.getParentFile();
        if (parent != null) parent.mkdirs();

        if (this.directoryFile)
            return this.file.mkdir(); // Create a directory
        else
            return this.file.createNewFile(); // Create a file
    }
}
