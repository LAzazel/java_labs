package lab5;

import java.io.IOException;
import java.io.FilterWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

public final class CaesarCipher {
    private CaesarCipher() {
    }

    public static String encrypt(String text, char key) throws IOException {
        return transform(text, key, true);
    }

    public static String decrypt(String text, char key) throws IOException {
        return transform(text, key, false);
    }

    public static void encrypt(Reader input, Writer output, char key) throws IOException {
        if (input == null || output == null) {
            throw new IllegalArgumentException("Потоки не можуть бути null");
        }
        try (Reader reader = input) {
            FilterWriter writer = new EncryptionFilterWriter(output, key);
            copy(reader, writer);
            writer.flush();
        }
    }

    public static void decrypt(Reader input, Writer output, char key) throws IOException {
        if (input == null || output == null) {
            throw new IllegalArgumentException("Потоки не можуть бути null");
        }
        try (Reader reader = new DecryptionFilterReader(input, key)) {
            copy(reader, output);
            output.flush();
        }
    }

    private static String transform(String text, char key, boolean encrypt) throws IOException {
        if (text == null) {
            throw new IllegalArgumentException("Текст не може бути null");
        }
        StringWriter output = new StringWriter();
        if (encrypt) {
            encrypt(new StringReader(text), output, key);
        } else {
            decrypt(new StringReader(text), output, key);
        }
        return output.toString();
    }

    private static void copy(Reader input, Writer output) throws IOException {
        char[] buffer = new char[1024];
        int count;
        while ((count = input.read(buffer)) != -1) {
            output.write(buffer, 0, count);
        }
    }

    static char shift(char value, char key, boolean encrypt) {
        return (char) (encrypt ? value + key : value - key);
    }
}
