package lab6;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EnglishUkrainianTranslator {
    private static final Pattern WORD_PATTERN = Pattern.compile("[\\p{L}]+");
    private final HashMap<String, String> dictionary = new HashMap<>();

    public void addWord(String englishWord, String ukrainianWord) {
        if (englishWord == null || ukrainianWord == null
                || englishWord.isBlank() || ukrainianWord.isBlank()
                || !englishWord.matches("[A-Za-z]+")) {
            throw new IllegalArgumentException("Слова мають бути непорожніми, а англійське слово — містити лише літери.");
        }

        dictionary.put(normalize(englishWord), ukrainianWord.trim());
    }

    public String translate(String phrase) {
        if (phrase == null) {
            throw new IllegalArgumentException("Фраза не може бути null.");
        }

        Matcher matcher = WORD_PATTERN.matcher(phrase);
        StringBuffer result = new StringBuffer();
        while (matcher.find()) {
            String sourceWord = matcher.group();
            String translation = dictionary.get(normalize(sourceWord));
            if (translation != null) {
                translation = preserveCapitalization(sourceWord, translation);
                matcher.appendReplacement(result, Matcher.quoteReplacement(translation));
            }
        }
        matcher.appendTail(result);
        return result.toString();
    }

    public Map<String, String> getDictionary() {
        return Map.copyOf(dictionary);
    }

    private String normalize(String word) {
        return word.toLowerCase(Locale.ENGLISH);
    }

    private String preserveCapitalization(String sourceWord, String translation) {
        if (sourceWord.equals(sourceWord.toUpperCase(Locale.ENGLISH))) {
            return translation.toUpperCase(Locale.ROOT);
        }
        if (Character.isUpperCase(sourceWord.charAt(0))) {
            return Character.toUpperCase(translation.charAt(0)) + translation.substring(1);
        }
        return translation;
    }
}
