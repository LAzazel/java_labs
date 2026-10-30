package lab3;

import lab3.model.PassRegistry;
import lab3.model.PassStatistics;
import lab3.model.SkiPass;
import lab3.model.SkiPassType;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Lab3 {
    public static void main(String[] args) {
        PassRegistry registry = new PassRegistry();
        Turnstile turnstile = new Turnstile();
        SkiPass pass = registry.issue(SkiPassType.WEEKDAY_10_RIDES, LocalDate.of(2026, 10, 26));

        System.out.println("Виданий ski-pass: " + pass.getId());
        System.out.println("Прохід: " + turnstile.allowById(pass.getId(), registry,
                LocalDateTime.of(2026, 10, 26, 10, 0), true));

        PassStatistics statistics = turnstile.getStatistics();
        System.out.println("Дозволено: " + statistics.getAllowed());
        System.out.println("Відмовлено: " + statistics.getDenied());
        System.out.println("За типами: " + statistics.getAllowedByType());
    }
}
