package com.everest.airline;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class Data {
    static String filePath = "/Users/Pravallika/Documents/Assignments/airlines/src/main/java/com/everest/airline/flights";
    static File directory = new File(filePath);

    public static void writeDataToFile(long number, String toReplace, String toBeReplaced) throws IOException {
        File[] files = directory.listFiles((File pathname) -> pathname.getName().equals(String.valueOf(number)));
        String content;
        assert files != null;
        File file = new File(files[0].getPath());
        content = Files.readString(Path.of(file.getPath()), Charset.defaultCharset());
        content = content.replaceAll(toReplace, toBeReplaced);
        Files.write(Path.of(file.getPath()), content.getBytes(StandardCharsets.UTF_8));
    }
}
