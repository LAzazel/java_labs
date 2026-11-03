package lab6;

import java.util.Scanner;

public class Lab6 {
    public static void main(String[] args) {
        EnglishUkrainianTranslator translator = new EnglishUkrainianTranslator();
        addDefaultWords(translator);

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("=== Лабораторна робота 6 ===");
            System.out.print("Введіть кількість власних пар слів (0, якщо не потрібно): ");
            int wordCount = readNonNegativeInt(scanner);

            for (int i = 1; i <= wordCount; i++) {
                System.out.print("Англійське слово " + i + ": ");
                String englishWord = readLine(scanner);
                System.out.print("Український переклад " + i + ": ");
                String ukrainianWord = readLine(scanner);
                try {
                    translator.addWord(englishWord, ukrainianWord);
                } catch (IllegalArgumentException exception) {
                    System.out.println("Пару пропущено: " + exception.getMessage());
                    i--;
                }
            }

            System.out.print("Введіть фразу англійською мовою: ");
            String phrase = readLine(scanner);
            System.out.println("Переклад: " + translator.translate(phrase));
        }
    }

    private static void addDefaultWords(EnglishUkrainianTranslator translator) {
        translator.addWord("hello", "привіт");
        translator.addWord("world", "світ");
        translator.addWord("java", "джава");
        translator.addWord("student", "студент");
        translator.addWord("learns", "вивчає");
        translator.addWord("programming", "програмування");
    }

    private static int readNonNegativeInt(Scanner scanner) {
        while (true) {
            String value = readLine(scanner);
            try {
                int number = Integer.parseInt(value);
                if (number >= 0) {
                    return number;
                }
            } catch (NumberFormatException ignored) {
            }
            System.out.print("Введіть ціле невід'ємне число: ");
        }
    }

    private static String readLine(Scanner scanner) {
        if (!scanner.hasNextLine()) {
            throw new IllegalArgumentException("Вхідний потік завершено.");
        }
        return scanner.nextLine().trim();
    }
}
