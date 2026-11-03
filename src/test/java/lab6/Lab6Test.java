package lab6;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class Lab6Test {
    @Test
    void translatesKnownWordsAndKeepsUnknownWords() {
        EnglishUkrainianTranslator translator = new EnglishUkrainianTranslator();
        translator.addWord("hello", "привіт");
        translator.addWord("world", "світ");

        assertEquals("привіт, світ!", translator.translate("hello, world!"));
    }

    @Test
    void translatesWordsWithoutChangingPunctuationAndCase() {
        EnglishUkrainianTranslator translator = new EnglishUkrainianTranslator();
        translator.addWord("java", "джава");

        assertEquals("Джава ДЖАВА.", translator.translate("Java JAVA."));
    }

    @Test
    void laterPairReplacesPreviousTranslation() {
        EnglishUkrainianTranslator translator = new EnglishUkrainianTranslator();
        translator.addWord("book", "книга");
        translator.addWord("BOOK", "підручник");

        assertEquals("підручник", translator.translate("book"));
        assertEquals("підручник", translator.getDictionary().get("book"));
    }

    @Test
    void rejectsInvalidWordPair() {
        EnglishUkrainianTranslator translator = new EnglishUkrainianTranslator();

        assertThrows(IllegalArgumentException.class, () -> translator.addWord("hello world", "привіт"));
        assertThrows(IllegalArgumentException.class, () -> translator.addWord("", "привіт"));
    }
}
