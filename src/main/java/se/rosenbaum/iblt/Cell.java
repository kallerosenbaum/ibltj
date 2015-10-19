package se.rosenbaum.iblt;

import se.rosenbaum.iblt.data.Data;
import se.rosenbaum.iblt.hash.HashFunction;

/**
 * User: kalle
 * Date: 9/11/14 6:41 PM
 */
public class Cell<K extends Data, V extends Data> {
    private int count;
    private K keySum;
    private V valueSum;
    private Data hashKeySum;
    private HashFunction<K, ? extends Data> hashFunction;

    public Cell(K initialKey, V initialValue, Data initialHashKey, HashFunction<K, ? extends Data> hashFunction, int initialCount) {
        if (initialKey == null) {
            throw new RuntimeException("initialKey must not be null");
        }
        if (initialValue == null) {
            throw new RuntimeException("zeroValueata must not be null");
        }
        if (initialHashKey == null) {
            throw new RuntimeException("initialHashKey must not be null");
        }
        keySum = initialKey;
        valueSum = initialValue;
        hashKeySum = initialHashKey;
        this.hashFunction = hashFunction;
        this.count = initialCount;
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

    public Data getHashKeySum() {
        return hashKeySum;
    }
}
