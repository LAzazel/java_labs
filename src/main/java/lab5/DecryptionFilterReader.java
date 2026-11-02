package lab5;

import java.io.FilterReader;
import java.io.IOException;
import java.io.Reader;

final class DecryptionFilterReader extends FilterReader {
    private final char key;

    DecryptionFilterReader(Reader input, char key) {
        super(input);
        this.key = key;
    }

    @Override
    public int read() throws IOException {
        int value = super.read();
        return value == -1 ? -1 : CaesarCipher.shift((char) value, key, false);
    }

    @Override
    public int read(char[] buffer, int offset, int length) throws IOException {
        int count = super.read(buffer, offset, length);
        for (int i = 0; i < count; i++) {
            buffer[offset + i] = CaesarCipher.shift(buffer[offset + i], key, false);
        }
        return count;
    }
}
