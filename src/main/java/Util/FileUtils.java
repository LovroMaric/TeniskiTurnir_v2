package Util;

import java.nio.file.Files;
import java.nio.file.Path;

public class FileUtils {

    public static void deleteImage(String path) {
        try {
            Files.deleteIfExists(Path.of(path));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
