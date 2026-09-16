package lab1;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Lab1 {
    public static String[] findWordWithMinimalDistinctChars(String input) {
        if (input == null || input.trim().isEmpty()) {
            return new String[0];
        }

        String[] words = input.trim().split("\\s+");
        String bestWord = null;
        int minDistinctCount = Integer.MAX_VALUE;

        for (String word : words) {
            int distinctCount = countDistinctChars(word);
            if (distinctCount < minDistinctCount) {
                minDistinctCount = distinctCount;
                bestWord = word;
            }
        }

        return bestWord == null ? new String[0] : new String[]{bestWord};
    }

    static int countDistinctChars(String word) {
        Set<Character> uniqueChars = new HashSet<>();

        for (int i = 0; i < word.length(); i++) {
            uniqueChars.add(word.charAt(i));
        }

        return uniqueChars.size();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        System.out.println(Arrays.toString(findWordWithMinimalDistinctChars(input)));
        scanner.close();
    }
}
