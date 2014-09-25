package se.rosenbaum.iblt.hash;

import se.rosenbaum.iblt.data.Data;

/**
 * User: kalle
 * Date: 9/11/14 7:13 PM
 */
public interface HashFunctions<D extends Data> {
    public int getHashFunctionCount();
    public int hash(int hashFunctionNumber, D data);
}
