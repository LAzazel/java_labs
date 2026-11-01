package lab4;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Road {
    public final List<Vehicle<? extends Human>> carsInRoad = new ArrayList<>();

    public int getCountOfHumans() {
        return carsInRoad.stream()
                .mapToInt(Vehicle::getOccupiedSeats)
                .sum();
    }

    public void addCarToRoad(Vehicle<? extends Human> vehicle) {
        carsInRoad.add(Objects.requireNonNull(vehicle, "Транспортний засіб не може бути null"));
    }

    public void removeCarFromRoad(Vehicle<? extends Human> vehicle) {
        if (!carsInRoad.remove(vehicle)) {
            throw new IllegalArgumentException("Транспортного засобу немає на дорозі");
        }
    }
}
