package lab4;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Lab4Test {
    @Test
    void vehicleReportsCapacityAndOccupiedSeats() {
        Taxi<Human> taxi = new Taxi<>(2);
        Human passenger = new Human();

        assertEquals(2, taxi.getMaxSeats());
        taxi.board(passenger);
        assertEquals(1, taxi.getOccupiedSeats());
        taxi.disembark(passenger);
        assertEquals(0, taxi.getOccupiedSeats());
    }

    @Test
    void boardingFullVehicleThrowsException() {
        Bus<Human> bus = new Bus<>(1);
        bus.board(new Human());

        assertThrows(IllegalStateException.class, () -> bus.board(new Human()));
    }

    @Test
    void specialisedCarsAcceptOnlyTheirPassengerType() {
        FireEngine fireEngine = new FireEngine(2);
        PoliceCar policeCar = new PoliceCar(2);

        fireEngine.board(new Firefighter());
        policeCar.board(new PoliceOfficer());

        assertEquals(1, fireEngine.getOccupiedSeats());
        assertEquals(1, policeCar.getOccupiedSeats());
    }

    @Test
    void roadCountsPassengersInAllVehicles() {
        Road road = new Road();
        Bus<Human> bus = new Bus<>(3);
        Taxi<Human> taxi = new Taxi<>(2);
        bus.board(new Human());
        bus.board(new Firefighter());
        taxi.board(new PoliceOfficer());

        road.addCarToRoad(bus);
        road.addCarToRoad(taxi);

        assertEquals(3, road.getCountOfHumans());
    }

    @Test
    void disembarkingUnknownPassengerThrowsException() {
        Taxi<Human> taxi = new Taxi<>(1);

        assertThrows(IllegalArgumentException.class, () -> taxi.disembark(new Human()));
    }
}
