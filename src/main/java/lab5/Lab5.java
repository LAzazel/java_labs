package lab5;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import java.util.Scanner;

public class Lab5 {
    private final Scanner scanner;

    public Lab5() {
        scanner = new Scanner(System.in);
    }

    public static void main(String[] args) {
        new Lab5().run();
    }

    private void run() {
        while (true) {
            System.out.println("\n=== Лабораторна робота 5 ===");
            System.out.println("1. Рядок із максимальною кількістю слів");
            System.out.println("2. Шифрування символьного потоку");
            System.out.println("3. Частота тегів за URL");
            System.out.println("0. Вийти");
            String choice = read("Виберіть дію: ");
            try {
                switch (choice) {
                    case "1" -> findMaxWordsLine();
                    case "2" -> cipher();
                    case "3" -> countTags();
                    case "0" -> { return; }
                    default -> System.out.println("Невірний пункт меню.");
                }
            } catch (IOException | IllegalArgumentException exception) {
                System.out.println("Помилка: " + exception.getMessage());
            }
        }
    }

    private void findMaxWordsLine() throws IOException {
        String line = MaxWordsLineFinder.find(Path.of(read("Шлях до файлу: ")));
        System.out.println(line == null ? "Файл порожній." : "Результат: " + line);
    }

    private void cipher() throws IOException {
        String text = read("Введіть текст: ");
        String key = read("Введіть ключовий символ: ");
        if (key.length() != 1) {
            throw new IllegalArgumentException("Ключ має бути одним символом.");
        }
        String encrypted = CaesarCipher.encrypt(text, key.charAt(0));
        System.out.println("Зашифровано: " + encrypted);
        System.out.println("Дешифровано: " + CaesarCipher.decrypt(encrypted, key.charAt(0)));
    }

    private void countTags() throws IOException {
        String url = read("Введіть URL: ");
        TagFrequencyAnalyzer analyzer = new TagFrequencyAnalyzer();
        Map<String, Integer> byName = analyzer.count(url, true);
        System.out.println("За назвою:");
        System.out.println(TagFrequencyAnalyzer.format(byName));
        System.out.println("За частотою:");
        System.out.println(TagFrequencyAnalyzer.format(TagFrequencyAnalyzer.sortByFrequency(byName)));
    }

    private String read(String prompt) {
        System.out.print(prompt);
        if (!scanner.hasNextLine()) {
            throw new IllegalArgumentException("Вхідний потік завершено.");
        }
        return scanner.nextLine().trim();
    }
}
