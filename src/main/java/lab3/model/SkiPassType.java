package lab3.model;

public enum SkiPassType {
    WEEKDAY_HALF_DAY_MORNING(false, 1, 0, 9, 13),
    WEEKDAY_HALF_DAY_AFTERNOON(false, 1, 0, 13, 17),
    WEEKDAY_DAY(false, 1, 0, 9, 17),
    WEEKDAY_TWO_DAYS(false, 2, 0, 9, 17),
    WEEKDAY_FIVE_DAYS(false, 5, 0, 9, 17),
    WEEKDAY_10_RIDES(false, 0, 10, 9, 17),
    WEEKDAY_20_RIDES(false, 0, 20, 9, 17),
    WEEKDAY_50_RIDES(false, 0, 50, 9, 17),
    WEEKDAY_100_RIDES(false, 0, 100, 9, 17),
    WEEKEND_HALF_DAY_MORNING(true, 1, 0, 9, 13),
    WEEKEND_HALF_DAY_AFTERNOON(true, 1, 0, 13, 17),
    WEEKEND_DAY(true, 1, 0, 9, 17),
    WEEKEND_TWO_DAYS(true, 2, 0, 9, 17),
    WEEKEND_10_RIDES(true, 0, 10, 9, 17),
    WEEKEND_20_RIDES(true, 0, 20, 9, 17),
    WEEKEND_50_RIDES(true, 0, 50, 9, 17),
    WEEKEND_100_RIDES(true, 0, 100, 9, 17),
    SEASON(false, 0, 0, 0, 24);

    private final boolean weekend;
    private final int validDays;
    private final int initialRides;
    private final int openingHour;
    private final int closingHour;

    SkiPassType(boolean weekend, int validDays, int initialRides, int openingHour, int closingHour) {
        this.weekend = weekend;
        this.validDays = validDays;
        this.initialRides = initialRides;
        this.openingHour = openingHour;
        this.closingHour = closingHour;
    }

    public boolean isWeekend() {
        return weekend;
    }

    public int getValidDays() {
        return validDays;
    }

    public int getInitialRides() {
        return initialRides;
    }

    public boolean countsRides() {
        return initialRides > 0;
    }

    public boolean allowsTime(java.time.LocalTime time) {
        return !time.isBefore(java.time.LocalTime.of(openingHour, 0))
                && time.isBefore(java.time.LocalTime.of(closingHour, 0));
    }
}
