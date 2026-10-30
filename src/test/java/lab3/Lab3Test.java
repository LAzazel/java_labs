package lab3;

import lab3.model.PassRegistry;
import lab3.model.SkiPass;
import lab3.model.SkiPassType;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class Lab3Test {
    @Test
    void validRidePassAllowsEntryAndConsumesCredit() {
        PassRegistry registry = new PassRegistry();
        SkiPass pass = registry.issue(SkiPassType.WEEKDAY_10_RIDES, LocalDate.of(2026, 10, 30));
        Turnstile turnstile = new Turnstile();

        assertTrue(turnstile.allow(pass, LocalDateTime.of(2026, 10, 30, 10, 0)));
        assertEquals(9, pass.getRemainingRides());
        assertEquals(1, turnstile.getStatistics().getAllowed());
    }

    @Test
    void expiredBlockedUnreadableAndEmptyPassesAreDenied() {
        PassRegistry registry = new PassRegistry();
        SkiPass pass = registry.issue(SkiPassType.WEEKDAY_10_RIDES, LocalDate.of(2026, 10, 29));
        Turnstile turnstile = new Turnstile();

        assertFalse(turnstile.allow(pass, LocalDateTime.of(2026, 10, 29, 10, 0), false));
        registry.block(pass.getId());
        assertFalse(turnstile.allow(pass, LocalDateTime.of(2026, 10, 29, 10, 0)));
        assertFalse(turnstile.allow(pass, LocalDateTime.of(2026, 11, 2, 10, 0)));
        assertEquals(3, turnstile.getStatistics().getDenied());
    }

    @Test
    void weekendPassCannotBeUsedOnWeekday() {
        PassRegistry registry = new PassRegistry();
        SkiPass pass = registry.issue(SkiPassType.WEEKEND_DAY, LocalDate.of(2026, 10, 31));
        Turnstile turnstile = new Turnstile();

        assertTrue(turnstile.allow(pass, LocalDateTime.of(2026, 10, 31, 12, 0)));
        assertFalse(turnstile.allow(pass, LocalDateTime.of(2026, 11, 2, 12, 0)));
    }
}
