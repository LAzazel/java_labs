package lab2.model;

import java.time.LocalDate;

public class CuratorJournalEntry {
    private final String lastName;
    private final String firstName;
    private final LocalDate birthDate;
    private final String phoneNumber;
    private final Address address;

    public CuratorJournalEntry(String lastName, String firstName, LocalDate birthDate,
                              String phoneNumber, Address address) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.birthDate = birthDate;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Address getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return lastName + " " + firstName + ", дата народження: " + birthDate
                + ", тел.: " + phoneNumber + ", адреса: " + address;
    }
}
