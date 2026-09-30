package lab2;

import lab2.model.Address;
import lab2.model.CuratorJournal;
import lab2.model.CuratorJournalEntry;

import java.time.LocalDate;
import java.util.Scanner;
import java.util.function.Predicate;

public class Lab2 {
    private static final Scanner SCANNER = new Scanner(System.in);
    private final CuratorJournal journal = new CuratorJournal();

    public static void main(String[] args) {
        Lab2 app = new Lab2();
        app.run();
    }

    private void run() {
        boolean isRunning = true;
        while (isRunning) {
            printMenu();
            String choice = readLine("Виберіть дію: ");
            if (choice.isEmpty()) {
                System.out.println("Вхідний потік завершено. До побачення!");
                isRunning = false;
                continue;
            }

            switch (choice) {
                case "1" -> addEntry();
                case "2" -> displayAllEntries();
                case "3" -> {
                    System.out.println("До побачення!");
                    isRunning = false;
                }
                default -> System.out.println("Невірний вибір. Спробуйте ще раз.");
            }
        }
    }

    private void printMenu() {
        System.out.println();
        System.out.println("=== Журнал куратора ===");
        System.out.println("1. Додати запис");
        System.out.println("2. Показати всі записи");
        System.out.println("3. Вийти");
    }

    private void addEntry() {
        System.out.println("\nВведення нового запису:");

        String lastName = readValidatedText(
                "Прізвище студента: ",
                ConsoleInputValidator::isValidName,
                "Неправильне прізвище. Введіть літери, починаючи з великої літери."
        );

        String firstName = readValidatedText(
                "Ім'я студента: ",
                ConsoleInputValidator::isValidName,
                "Неправильне ім'я. Введіть літери, починаючи з великої літери."
        );

        LocalDate birthDate = readValidatedDate(
                "Дата народження студента (dd.MM.yyyy): ",
                "Невірний формат дати. Використовуйте dd.MM.yyyy."
        );

        String phone = readValidatedText(
                "Телефон студента: ",
                ConsoleInputValidator::isValidPhone,
                "Невірний формат телефону. Приклад: +380501234567"
        );

        String street = readValidatedText(
                "Вулиця: ",
                ConsoleInputValidator::isValidStreet,
                "Невірна вулиця. Використовуйте літери, цифри, пробіли або дефіс."
        );

        String house = readValidatedText(
                "Будинок: ",
                ConsoleInputValidator::isValidHouseNumber,
                "Невірний номер будинку."
        );

        String apartment = readValidatedText(
                "Квартира: ",
                ConsoleInputValidator::isValidApartmentNumber,
                "Невірний номер квартири."
        );

        CuratorJournalEntry entry = new CuratorJournalEntry(lastName, firstName, birthDate,
                phone, new Address(street, house, apartment));
        journal.addEntry(entry);

        System.out.println("Запис успішно додано до журналу.");
    }

    private void displayAllEntries() {
        if (journal.isEmpty()) {
            System.out.println("Журнал ще порожній.");
            return;
        }

        System.out.println("\nВсі записи журналу:");
        int index = 1;
        for (CuratorJournalEntry entry : journal.getEntries()) {
            System.out.println(index + ". " + entry);
            index++;
        }
    }

    private String readLine(String prompt) {
        System.out.print(prompt);
        if (!SCANNER.hasNextLine()) {
            return "";
        }
        return SCANNER.nextLine().trim();
    }

    private String readValidatedText(String prompt, Predicate<String> validator, String errorMessage) {
        while (true) {
            String userInput = readLine(prompt);
            if (userInput.isEmpty()) {
                System.out.println("Вхідний потік завершено. Завершення роботи.");
                System.exit(0);
            }
            if (validator.test(userInput)) {
                return userInput;
            }
            System.out.println(errorMessage);
        }
    }

    private LocalDate readValidatedDate(String prompt, String errorMessage) {
        while (true) {
            String userInput = readLine(prompt);
            if (userInput.isEmpty()) {
                System.out.println("Вхідний потік завершено. Завершення роботи.");
                System.exit(0);
            }
            LocalDate parsedDate = ConsoleInputValidator.parseBirthDate(userInput);
            if (parsedDate != null) {
                return parsedDate;
            }
            System.out.println(errorMessage);
        }
    }
}
