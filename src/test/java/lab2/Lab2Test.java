package lab2;

import lab2.model.Address;
import lab2.model.CuratorJournal;
import lab2.model.CuratorJournalEntry;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Lab2Test {
    @Test
    void validDataShouldCreateEntry() {
        CuratorJournal journal = new CuratorJournal();
        CuratorJournalEntry entry = new CuratorJournalEntry(
                "Іваненко",
                "Олексій",
                LocalDate.of(2001, 4, 12),
                "+380501234567",
                new Address("Гагаріна", "12", "45")
        );

        journal.addEntry(entry);

        assertEquals(1, journal.getEntries().size());
        assertEquals("Іваненко", journal.getEntries().get(0).getLastName());
        assertEquals("Олексій", journal.getEntries().get(0).getFirstName());
        assertEquals("Гагаріна, буд. 12, кв. 45", journal.getEntries().get(0).getAddress().toString());
    }

    @Test
    void validatorShouldAcceptCorrectParameters() {
        assertTrue(ConsoleInputValidator.isValidName("Петренко"));
        assertTrue(ConsoleInputValidator.isValidName("Марія"));
        assertTrue(ConsoleInputValidator.isValidPhone("+380661234567"));
        assertTrue(ConsoleInputValidator.isValidStreet("вул. Шевченка"));
        assertTrue(ConsoleInputValidator.isValidHouseNumber("15A"));
        assertTrue(ConsoleInputValidator.isValidApartmentNumber("34"));
        assertEquals(LocalDate.of(2003, 7, 21), ConsoleInputValidator.parseBirthDate("21.07.2003"));
    }
}
