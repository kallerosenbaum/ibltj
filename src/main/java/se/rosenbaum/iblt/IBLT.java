package se.rosenbaum.iblt;

import se.rosenbaum.iblt.data.Data;

import java.util.Map;

public class IBLT<K extends Data, V extends Data> {
    private Cell<K, V>[] cells;
    private HashFunction<K> hashFunctions;

    public IBLT(Cell<K, V>[] cells, HashFunction<K> hashFunctions) {
        this.cells = cells;
        this.hashFunctions = hashFunctions;
    }

    public void insert(K key, V value) {
        for (int i = 0; i < hashFunctions.getHashFunctionCount(); i++) {
            int cellIndex = hashFunctions.hash(i, key);
            cells[cellIndex].add(key, value);
        }
    }

    public void delete(K key, V value) {
        for (int i = 0; i < hashFunctions.getHashFunctionCount(); i++) {
            int cellIndex = hashFunctions.hash(i, key);
            cells[cellIndex].subtract(key, value);
        }
    }

    public V get(K key) throws NotFoundException {
        for (int i = 0; i < hashFunctions.getHashFunctionCount(); i++) {
            int cellIndex = hashFunctions.hash(i, key);
            Cell<K, V> cell = cells[cellIndex];
            int count = cell.getCount();
            if (count == 0) {
                return null;
            } else if (count == 1) {
                if (cell.getKeySum().equals(key)) {
                    V valueSum = cell.getValueSum();
                    return (V)valueSum.copy();
                } else {
                    return null;
                }
            }
        }
        // No cell contains ONLY key but it may still be in there. We're not sure.
        throw new NotFoundException(key);
    }

    public Map<K,V> listEntries() {
        return null;
    }
}
