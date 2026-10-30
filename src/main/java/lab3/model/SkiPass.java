package lab3.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public final class SkiPass {
    private final UUID id;
    private final SkiPassType type;
    private final LocalDate validFrom;
    private final LocalDate validUntil;
    private int remainingRides;
    private boolean blocked;

    public SkiPass(UUID id, SkiPassType type, LocalDate validFrom, LocalDate validUntil) {
        this.id = Objects.requireNonNull(id);
        this.type = Objects.requireNonNull(type);
        this.validFrom = Objects.requireNonNull(validFrom);
        this.validUntil = Objects.requireNonNull(validUntil);
        if (validUntil.isBefore(validFrom)) {
            throw new IllegalArgumentException("Дата завершення не може бути раніше дати початку");
        }
        this.remainingRides = type.getInitialRides();
    }

    public static SkiPass create(SkiPassType type, LocalDate validFrom) {
        LocalDate validUntil;
        if (type == SkiPassType.SEASON) {
            validUntil = validFrom.withMonth(12).withDayOfMonth(31);
        } else {
            int duration = type.countsRides() ? 1 : type.getValidDays();
            validUntil = validFrom.plusDays(duration - 1L);
        }
        return new SkiPass(UUID.randomUUID(), type, validFrom, validUntil);
    }

    public UUID getId() { return id; }
    public SkiPassType getType() { return type; }
    public LocalDate getValidFrom() { return validFrom; }
    public LocalDate getValidUntil() { return validUntil; }
    public int getRemainingRides() { return remainingRides; }
    public boolean isBlocked() { return blocked; }

    public boolean isValidAt(LocalDateTime moment) {
        if (blocked || moment.toLocalDate().isBefore(validFrom)
                || moment.toLocalDate().isAfter(validUntil)
                || !type.allowsTime(moment.toLocalTime())) {
            return false;
        }
        if (type != SkiPassType.SEASON && type.isWeekend()
                != (moment.getDayOfWeek().getValue() >= 6)) {
            return false;
        }
        return !type.countsRides() || remainingRides > 0;
    }

    public void useRide() {
        if (!type.countsRides()) {
            return;
        }
        if (remainingRides == 0) {
            throw new IllegalStateException("На ski-pass не залишилося підйомів");
        }
        remainingRides--;
    }

    public void block() {
        blocked = true;
    }
}
