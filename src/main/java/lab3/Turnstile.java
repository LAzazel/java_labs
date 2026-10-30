package lab3;

import lab3.model.PassStatistics;
import lab3.model.PassRegistry;
import lab3.model.SkiPass;
import lab3.model.SkiPassType;

import java.time.LocalDateTime;
import java.util.EnumMap;
import java.util.Map;
import java.util.UUID;

public class Turnstile {
    private long allowed;
    private long denied;
    private final Map<SkiPassType, Long> allowedByType = new EnumMap<>(SkiPassType.class);
    private final Map<SkiPassType, Long> deniedByType = new EnumMap<>(SkiPassType.class);

    public boolean allow(SkiPass pass, LocalDateTime moment) {
        return allow(pass, moment, true);
    }

    public boolean allow(SkiPass pass, LocalDateTime moment, boolean dataReadable) {
        boolean accepted = dataReadable && pass != null && pass.isValidAt(moment);
        SkiPassType type = pass == null ? null : pass.getType();
        if (accepted) {
            pass.useRide();
            allowed++;
            increment(allowedByType, type);
        } else {
            denied++;
            if (type != null) {
                increment(deniedByType, type);
            }
        }
        return accepted;
    }

    public PassStatistics getStatistics() {
        return new PassStatistics(allowed, denied, allowedByType, deniedByType);
    }

    public boolean allowById(UUID id, Map<UUID, SkiPass> readableCards, LocalDateTime moment) {
        return allow(readableCards.get(id), moment);
    }

    public boolean allowById(UUID id, PassRegistry registry, LocalDateTime moment, boolean dataReadable) {
        return allow(registry.find(id), moment, dataReadable);
    }

    private void increment(Map<SkiPassType, Long> counters, SkiPassType type) {
        counters.merge(type, 1L, Long::sum);
    }
}
