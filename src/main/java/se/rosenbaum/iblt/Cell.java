package se.rosenbaum.iblt;

import se.rosenbaum.iblt.data.Data;

/**
 * User: kalle
 * Date: 9/11/14 6:41 PM
 */
public class Cell<K extends Data, V extends Data> {
    private int count = 0;
    private K keySum;
    private V valueSum;

    public Cell(K zeroKeyData, V zeroValueData) {
        if (zeroKeyData == null) {
            throw new RuntimeException("zeroKeyData must not be null");
        }
        if (zeroValueData == null) {
            throw new RuntimeException("zeroValueata must not be null");
        }
        keySum = zeroKeyData;
        valueSum = zeroValueData;
    }

    public void add(K key, V value) {
        count++;
        keySum.add(key);
        valueSum.add(value);
    }

    public void subtract(K key, V value) {
        count--;
        keySum.subtract(key);
        valueSum.subtract(value);
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
        return "[" + count + ", " + getKeySum() + ", " + getValueSum() + "]";
    }

}
