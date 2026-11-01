package lab4;

public class Bus<T extends Human> extends Vehicle<T> {
    public Bus(int maxSeats) {
        super(maxSeats);
    }
}
