package lab5;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public final class MaxWordsLineFinder {
    private MaxWordsLineFinder() {
    }

    public static String find(Path file) throws IOException {
        if (file == null) {
            throw new IllegalArgumentException("Файл не може бути null");
        }

        String result = null;
        int maxWords = -1;
        try (BufferedReader reader = Files.newBufferedReader(file, StandardCharsets.UTF_8)) {
            String line;
            while ((line = reader.readLine()) != null) {
                int words = countWords(line);
                if (words > maxWords) {
                    maxWords = words;
                    result = line;
                }
            }
        }
        return result;
    }

    static int countWords(String line) {
        if (line == null || line.isBlank()) {
            return 0;
        }
        return line.trim().split("\\s+").length;
    }
}
