package lab7;

import java.util.Arrays;
import java.util.Scanner;

public class Lab7 {
    public static String[] findWordsWithIncreasingCharacterCodes(String input) {
        if (input == null || input.isBlank()) {
            return new String[0];
        }

        return Arrays.stream(input.trim().split("\\s+"))
                .filter(word -> hasIncreasingCharacterCodes(word))
                .toArray(String[]::new);
    }

    private static boolean hasIncreasingCharacterCodes(String word) {
        return word.length() < 2
                || java.util.stream.IntStream.range(1, word.length())
                .allMatch(index -> word.charAt(index - 1) < word.charAt(index));
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            String input = scanner.hasNextLine() ? scanner.nextLine() : "";
            System.out.println(Arrays.toString(findWordsWithIncreasingCharacterCodes(input)));
        }
    }
}
