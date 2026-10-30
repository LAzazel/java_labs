package lab3.model;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

public final class PassStatistics {
    private final long allowed;
    private final long denied;
    private final Map<SkiPassType, Long> allowedByType;
    private final Map<SkiPassType, Long> deniedByType;

    public PassStatistics(long allowed, long denied,
                          Map<SkiPassType, Long> allowedByType,
                          Map<SkiPassType, Long> deniedByType) {
        this.allowed = allowed;
        this.denied = denied;
        this.allowedByType = immutableCopy(allowedByType);
        this.deniedByType = immutableCopy(deniedByType);
    }

    public long getAllowed() { return allowed; }
    public long getDenied() { return denied; }
    public Map<SkiPassType, Long> getAllowedByType() { return allowedByType; }
    public Map<SkiPassType, Long> getDeniedByType() { return deniedByType; }

    private static Map<SkiPassType, Long> immutableCopy(Map<SkiPassType, Long> source) {
        Map<SkiPassType, Long> copy = new EnumMap<>(SkiPassType.class);
        copy.putAll(source);
        return Collections.unmodifiableMap(copy);
    }
}
