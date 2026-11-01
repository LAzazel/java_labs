package lab4;

public class Taxi<T extends Human> extends Car<T> {
    public Taxi(int maxSeats) {
        super(maxSeats);
    }
}
