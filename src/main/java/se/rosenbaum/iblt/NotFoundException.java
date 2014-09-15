package se.rosenbaum.iblt;

import se.rosenbaum.iblt.data.Data;

/**
 * Used to communicate that we cannot be sure whether the key exists or not.
 */
public class NotFoundException extends Exception {
    private Data key;
    public NotFoundException(Data key) {
        this.key = key;
    }

    public Data getKey() {
        return key;
    }
}
