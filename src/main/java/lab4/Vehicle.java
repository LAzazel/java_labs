package lab4;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Vehicle<T extends Human> {
    private final int maxSeats;
    private final List<T> passengers = new ArrayList<>();

    public Vehicle(int maxSeats) {
        if (maxSeats < 1) {
            throw new IllegalArgumentException("Кількість місць повинна бути додатною");
        }
        this.maxSeats = maxSeats;
    }

    public int getMaxSeats() {
        return maxSeats;
    }

    public int getOccupiedSeats() {
        return passengers.size();
    }

    public int getFreeSeats() {
        return maxSeats - passengers.size();
    }

    public int getPassengersCount() {
        return getOccupiedSeats();
    }

    public List<T> getPassengers() {
        return Collections.unmodifiableList(passengers);
    }

    public void board(T passenger) {
        Objects.requireNonNull(passenger, "Пасажир не може бути null");
        if (passengers.size() >= maxSeats) {
            throw new IllegalStateException("У транспортному засобі немає вільних місць");
        }
        passengers.add(passenger);
    }

    public void addPassenger(T passenger) {
        board(passenger);
    }

    public void disembark(T passenger) {
        if (!passengers.remove(passenger)) {
            throw new IllegalArgumentException("Вказаний пасажир не сидить у транспортному засобі");
        }
    }

    public void removePassenger(T passenger) {
        disembark(passenger);
    }
}
