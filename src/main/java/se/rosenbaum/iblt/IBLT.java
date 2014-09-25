package se.rosenbaum.iblt;

import se.rosenbaum.iblt.data.Data;
import se.rosenbaum.iblt.exception.WrongCellIndexException;
import se.rosenbaum.iblt.hash.HashFunctions;
import se.rosenbaum.iblt.util.ResidualData;

public class IBLT<K extends Data, V extends Data> {
    private Cell<K, V>[] cells;
    private HashFunctions<K> hashFunctions;

    public IBLT(Cell<K, V>[] cells, HashFunctions<K> hashFunctions) {
        this.cells = cells;
        this.hashFunctions = hashFunctions;
    }

    public void insert(K key, V value) {
        for (int i = 0; i < hashFunctions.getHashFunctionCount(); i++) {
            int cellIndex = hash(i, key);
            cells[cellIndex].add(key, value);
        }
    }

    private int hash(int i, K key) {
        int cellIndex = hashFunctions.hash(i, key);
        if (cellIndex < 0 || cellIndex >= cells.length) {
            throw new WrongCellIndexException(cellIndex, cells.length);
        }
        return cellIndex;
    }

    public void delete(K key, V value) {
        for (int i = 0; i < hashFunctions.getHashFunctionCount(); i++) {
            int cellIndex = hash(i, key);
            cells[cellIndex].subtract(key, value);
        }
    }

    public V get(K key) throws NotFoundException {
        for (int i = 0; i < hashFunctions.getHashFunctionCount(); i++) {
            int cellIndex = hash(i, key);
            Cell<K, V> cell = cells[cellIndex];
            if (!cell.isUnambiguous()) {
                continue;
            }
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

    /**
     * Tries to list all entries. If all entries can be listed, a ResidualData is returned. This operation will
     * alter the IBLT itself, so you can only use it once.
     *
     * @return If all entries can be listed, a ResidualData containing all entries. Otherwise null is returned.
     */
    public ResidualData<K, V> listEntries() {
        ResidualData<K, V> residualData = new ResidualData<K, V>();

        boolean entryFound = true;
        while (entryFound) {
            entryFound = false;
            boolean allZero = true;
            for (Cell<K, V> cell : cells) {
                if (!cell.isUnambiguous()) {
                    allZero = false;
                    continue;
                }
                if (cell.getCount() == 1) {
                    K key = (K) cell.getKeySum().copy();
                    V value = (V) cell.getValueSum().copy();
                    residualData.putExtra(key, value);
                    this.delete(key, value);
                    allZero = false;
                    entryFound = true;
                } else if (cell.getCount() == -1) {
                    K key = (K) cell.getKeySum().invertCopy();
                    V value = (V) cell.getValueSum().invertCopy();
                    residualData.putAbsent(key, value);
                    this.insert(key, value);
                    allZero = false;
                    entryFound = true;
                }
            }
            if (allZero) {
                return residualData;
            }
        }
        return null;
    }
}
