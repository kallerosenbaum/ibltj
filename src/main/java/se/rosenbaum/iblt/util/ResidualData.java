package se.rosenbaum.iblt.util;

import se.rosenbaum.iblt.data.Data;

import java.util.HashMap;
import java.util.Map;

public class ResidualData<K extends Data, V extends Data> {
    private Map<K, V> extraEntries = new HashMap<K, V>();
    private Map<K, V> absentEntries = new HashMap<K, V>();

    public void putExtra(K key, V value) {
        extraEntries.put(key, value);
    }

    public void putAbsent(K key, V value) {
        absentEntries.put(key, value);
    }

    public Map<K, V> getExtraEntries() {
        return extraEntries;
    }

    public Map<K, V> getAbsentEntries() {
        return absentEntries;
    }
}
