package lab5;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class TagFrequencyAnalyzer {
    private static final Pattern OPENING_TAG =
            Pattern.compile("<\\s*([A-Za-z][A-Za-z0-9:-]*)\\b[^>]*>");

    public Map<String, Integer> count(URL url) throws IOException {
        if (url == null) {
            throw new IllegalArgumentException("URL не може бути null");
        }
        try (InputStream input = url.openStream()) {
            return count(new String(input.readAllBytes(), StandardCharsets.UTF_8));
        }
    }

    public Map<String, Integer> count(String html) {
        if (html == null) {
            throw new IllegalArgumentException("HTML не може бути null");
        }
        Map<String, Integer> frequencies = new TreeMap<>();
        Matcher matcher = OPENING_TAG.matcher(html);
        while (matcher.find()) {
            String tag = matcher.group(1).toLowerCase();
            frequencies.merge(tag, 1, Integer::sum);
        }
        return frequencies;
    }

    public Map<String, Integer> count(String url, boolean readFromUrl) throws IOException {
        if (!readFromUrl) {
            return count(url);
        }
        return count(URI.create(url).toURL());
    }

    public static Map<String, Integer> sortByFrequency(Map<String, Integer> frequencies) {
        Map<String, Integer> result = new LinkedHashMap<>();
        frequencies.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue()
                        .thenComparing(Map.Entry.comparingByKey()))
                .forEach(entry -> result.put(entry.getKey(), entry.getValue()));
        return result;
    }

    public static String format(Map<String, Integer> frequencies) {
        return frequencies.entrySet().stream()
                .map(entry -> entry.getKey() + " = " + entry.getValue())
                .reduce((left, right) -> left + System.lineSeparator() + right)
                .orElse("(тегів не знайдено)");
    }
}
