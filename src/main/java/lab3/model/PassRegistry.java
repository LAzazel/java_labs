package lab3.model;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PassRegistry {
    private final Map<UUID, SkiPass> passes = new HashMap<>();

    public SkiPass issue(SkiPassType type, LocalDate validFrom) {
        SkiPass pass = SkiPass.create(type, validFrom);
        passes.put(pass.getId(), pass);
        return pass;
    }

    public void block(UUID id) {
        getRequired(id).block();
    }

    public SkiPass find(UUID id) {
        return passes.get(id);
    }

    public Collection<SkiPass> getIssuedPasses() {
        return Collections.unmodifiableCollection(passes.values());
    }

    private SkiPass getRequired(UUID id) {
        SkiPass pass = find(id);
        if (pass == null) {
            throw new IllegalArgumentException("Ski-pass не знайдено: " + id);
        }
        return pass;
    }
}
