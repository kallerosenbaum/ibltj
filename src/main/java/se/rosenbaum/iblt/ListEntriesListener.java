package se.rosenbaum.iblt;

import se.rosenbaum.iblt.data.Data;

public interface ListEntriesListener<K extends Data, V extends Data> {
    public void absentKeyDetected(K key, V value);
}
