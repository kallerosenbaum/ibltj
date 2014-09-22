package se.rosenbaum.iblt;

import se.rosenbaum.iblt.data.Data;
import se.rosenbaum.iblt.hash.HashFunction;
import se.rosenbaum.iblt.util.ResidualData;

import java.util.Map;
import java.util.TreeMap;

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

    public ResidualData<K, V> listEntries() {
        ResidualData<K, V> residualData = new ResidualData<K, V>();

        boolean found = true;
        while (found) {
            found = false;
            for (Cell<K, V> cell : cells) {
                 if (cell.getCount() == 1) {
                     K key = (K) cell.getKeySum().copy();
                     V value = (V) cell.getValueSum().copy();
                     residualData.putExtra(key, value);
                     this.delete(key, value);
                     found = true;
                 } else if (cell.getCount() == -1) {
                     K key = (K)cell.getKeySum().invertCopy();
                     V value = (V)cell.getValueSum().invertCopy();
                     residualData.putAbsent(key, value);
                     this.insert(key, value);
                     found = true;
                 }
            }
        }
        return residualData;
    }
}
