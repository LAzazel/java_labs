package lab5;

import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Lab5Test {
    @Test
    void findsFirstLineWithMostWords() throws Exception {
        Path file = Files.createTempFile("lab5", ".txt");
        Files.writeString(file, "one two\none two three\nlast");

        assertEquals("one two three", MaxWordsLineFinder.find(file));
        Files.deleteIfExists(file);
    }

    @Test
    void encryptsAndDecryptsUsingKeyCharacter() throws Exception {
        String encrypted = CaesarCipher.encrypt("Ab 9", (char) 1);

        assertEquals("Bc!:", encrypted);
        assertEquals("Ab 9", CaesarCipher.decrypt(encrypted, (char) 1));
    }

    @Test
    void countsOpeningTagsAndSortsByFrequency() {
        TagFrequencyAnalyzer analyzer = new TagFrequencyAnalyzer();
        Map<String, Integer> frequencies = analyzer.count("<html><body><p>x</p><p>y</p></body></html>");

        assertEquals(Map.of("body", 1, "html", 1, "p", 2), frequencies);
        assertEquals(Map.of("body", 1, "html", 1, "p", 2),
                TagFrequencyAnalyzer.sortByFrequency(frequencies));
    }
}
