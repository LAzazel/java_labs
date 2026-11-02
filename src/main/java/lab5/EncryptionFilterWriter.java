package lab5;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;

final class EncryptionFilterWriter extends FilterWriter {
    private final char key;

    EncryptionFilterWriter(Writer output, char key) {
        super(output);
        this.key = key;
    }

    @Override
    public void write(int value) throws IOException {
        super.write(CaesarCipher.shift((char) value, key, true));
    }

    @Override
    public void write(char[] buffer, int offset, int length) throws IOException {
        for (int i = 0; i < length; i++) {
            write(buffer[offset + i]);
        }
    }

    @Override
    public void write(String value, int offset, int length) throws IOException {
        for (int i = 0; i < length; i++) {
            write(value.charAt(offset + i));
        }
    }
}
