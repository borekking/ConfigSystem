import de.borekking.configSystem.FileLoader;

import org.junit.After;
import org.junit.Test;
import org.junit.Assert;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileLoadingTest {

    private final List<File> files = new ArrayList<>();

    @Test
    public void testNoParentsTXT() throws IOException {
        this.testNotDirectoryFile("test0.txt");
    }

    @Test
    public void testNoParentsJSON() throws IOException {
        this.testNotDirectoryFile("test1.json");
    }

    @Test
    public void testParentsTXT() throws IOException {
        this.testNotDirectoryFile("test00/test10/test10/test.json");
    }

    @Test
    public void testParentsJSON() throws IOException {
        this.testNotDirectoryFile("test01/test11/test21/test.json");
    }

    @Test
    public void testDirectory() throws IOException {
        this.testDirectoryFile("test02/test12/test22");
    }

    private void testNotDirectoryFile(String path) throws IOException {
        File file = new File(path);
        this.files.add(file);

        boolean created = new FileLoader(file, false).load();
        Assert.assertTrue(created && file.exists() && !file.isDirectory());
    }

    private void testDirectoryFile(String path) throws IOException {
        File file = new File(path);
        this.files.add(file);

        boolean created = new FileLoader(file, true).load();
        Assert.assertTrue(created
                && file.exists()
                && file.isDirectory());
    }

    @After
    public void deleteFiles() {
        for (File file : this.files) this.deleteFile(file);
        this.files.clear();
    }

    // Delete a file and all of its parents recursively
    private void deleteFile(File file) {
        file.delete();

        if (file.getParent() != null)
            this.deleteFile(file.getParentFile());
    }
}
