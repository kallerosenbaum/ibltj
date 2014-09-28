package se.rosenbaum.iblt;

import se.rosenbaum.iblt.data.Data;
import se.rosenbaum.iblt.hash.HashFunction;

/**
 * User: kalle
 * Date: 9/11/14 6:41 PM
 */
public class Cell<K extends Data, V extends Data> {
    private int count = 0;
    private K keySum;
    private V valueSum;
    private Data hashKeySum;
    private HashFunction<K, ? extends Data> hashFunction;

    public Cell(K zeroKeyData, V zeroValueData, Data zeroHashKeyData, HashFunction<K, ? extends Data> hashFunction) {
        if (zeroKeyData == null) {
            throw new RuntimeException("zeroKeyData must not be null");
        }
        if (zeroValueData == null) {
            throw new RuntimeException("zeroValueata must not be null");
        }
        if (zeroHashKeyData == null) {
            throw new RuntimeException("zeroHashKeyData must not be null");
        }
        keySum = zeroKeyData;
        valueSum = zeroValueData;
        hashKeySum = zeroHashKeyData;
        this.hashFunction = hashFunction;
    }

    public void add(K key, V value) {
        count++;
        keySum.add(key);
        valueSum.add(value);
        hashKeySum.add(hashFunction.hash(key));
    }

    public void subtract(K key, V value) {
        count--;
        keySum.subtract(key);
        valueSum.subtract(value);
        hashKeySum.subtract(hashFunction.hash(key));
    }

    public int getCount() {
        return count;
    }

    public K getKeySum() {
        return keySum;
    }

    public V getValueSum() {
        return valueSum;
    }

    public String toString() {
        return "[" + count + ", " + getKeySum() + ", " + getValueSum() + ", " + hashKeySum + " , " + (isUnambiguous() ? "OK" : "BAD") + "]";
    }

    public boolean isUnambiguous() {
        if (count == 0 && keySum.isZero() && valueSum.isZero() && hashKeySum.isZero()) {
            return true;
        } else if (count == 1 && hashKeySum.equals(hashFunction.hash(keySum))) {
            return true;
        } else if (count == -1 && hashKeySum.invertCopy().equals(hashFunction.hash((K)keySum.invertCopy()))) {
            return true;
        }
        return false;
    }
}
