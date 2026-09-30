package lab2;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

public final class ConsoleInputValidator {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.uuuu")
            .withResolverStyle(ResolverStyle.STRICT);

    private ConsoleInputValidator() {
    }

    public static boolean isValidName(String value) {
        return value != null && !value.isBlank() && value.matches("[A-ZА-ЯІЇЄҐ][A-Za-zА-Яа-яІіЇїЄєҐґ'-]*");
    }

    public static boolean isValidPhone(String value) {
        return value != null && value.matches("\\+?\\d{10,12}");
    }

    public static boolean isValidStreet(String value) {
        return value != null && !value.isBlank() && value.matches("[A-Za-zА-Яа-яІіЇЄєҐґ0-9 .'-]+");
    }

    public static boolean isValidHouseNumber(String value) {
        return value != null && !value.isBlank() && value.matches("[A-Za-zА-Яа-яІіЇЄєҐґ0-9/.-]+");
    }

    public static boolean isValidApartmentNumber(String value) {
        return isValidHouseNumber(value);
    }

    public static LocalDate parseBirthDate(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        try {
            return LocalDate.parse(value, DATE_FORMATTER);
        } catch (DateTimeParseException ex) {
            return null;
        }
    }
}
