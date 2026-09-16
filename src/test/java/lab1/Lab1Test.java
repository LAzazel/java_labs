package lab1;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

class Lab1Test {
    @Test
    void shouldReturnWordWithMinimalDistinctChars() {
        String input = "ab ba ccc a";
        assertArrayEquals(new String[]{"ccc"}, Lab1.findWordWithMinimalDistinctChars(input));
    }

    @Test
    void shouldReturnFirstWordWhenSeveralHaveSameMinimalCount() {
        String input = "one two three";
        assertArrayEquals(new String[]{"one"}, Lab1.findWordWithMinimalDistinctChars(input));
    }

    @Test
    void shouldReturnEmptyArrayForBlankInput() {
        assertArrayEquals(new String[]{}, Lab1.findWordWithMinimalDistinctChars("   "));
    }

    @Test
    void shouldHandleSingleWordInput() {
        assertArrayEquals(new String[]{"banana"}, Lab1.findWordWithMinimalDistinctChars("banana"));
    }
}
