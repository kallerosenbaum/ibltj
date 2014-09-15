package se.rosenbaum.iblt;

import se.rosenbaum.iblt.data.Data;

/**
 * User: kalle
 * Date: 9/11/14 7:13 PM
 */
public interface HashFunction<D extends Data> {
    public int getHashFunctionCount();
    public int hash(int hashFunctionNumber, D data);
}
