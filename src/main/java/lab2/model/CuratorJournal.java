package lab2.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CuratorJournal {
    private final List<CuratorJournalEntry> entries = new ArrayList<>();

    public void addEntry(CuratorJournalEntry entry) {
        entries.add(entry);
    }

    public List<CuratorJournalEntry> getEntries() {
        return Collections.unmodifiableList(entries);
    }

    public boolean isEmpty() {
        return entries.isEmpty();
    }
}
