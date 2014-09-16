package se.rosenbaum.iblt.util;

/**
 * Created with IntelliJ IDEA.
 * User: kalle
 * Date: 9/16/14
 * Time: 8:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class Tuple<K, V> {
    private K key;
    private V value;

    public Tuple(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    public boolean equals(Object other) {
        if (!(other instanceof Tuple)) {
            return false;
        }
        Tuple otherTuple = (Tuple)other;
        return key.equals(otherTuple.key) && value.equals(otherTuple.value);
    }
}
