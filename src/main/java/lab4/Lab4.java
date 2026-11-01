package lab4;

public class Lab4 {
    public static void main(String[] args) {
        Road road = new Road();
        Bus<Human> bus = new Bus<>(30);
        Taxi<Human> taxi = new Taxi<>(3);

        bus.addPassenger(new Human());
        bus.addPassenger(new Firefighter());
        taxi.addPassenger(new PoliceOfficer());

        road.addCarToRoad(bus);
        road.addCarToRoad(taxi);

        System.out.println("Кількість пасажирів на ділянці дороги: "
                + road.getCountOfHumans());
    }
}
