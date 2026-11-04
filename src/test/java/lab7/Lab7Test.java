package lab7;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class Lab7Test {
    @Test
    void returnsWordsWithStrictlyIncreasingCharacterCodes() {
        assertArrayEquals(
                new String[]{"abc", "ace", "абв"},
                Lab7.findWordsWithIncreasingCharacterCodes("abc cba ace abb абв"));
    }

    @Test
    void excludesWordsWithEqualOrDecreasingCharacterCodes() {
        assertArrayEquals(
                new String[]{"a"},
                Lab7.findWordsWithIncreasingCharacterCodes("aba abb ba a"));
    }

    @Test
    void returnsEmptyArrayForBlankOrNullInput() {
        assertArrayEquals(new String[0], Lab7.findWordsWithIncreasingCharacterCodes("  \t"));
        assertArrayEquals(new String[0], Lab7.findWordsWithIncreasingCharacterCodes(null));
    }
}
