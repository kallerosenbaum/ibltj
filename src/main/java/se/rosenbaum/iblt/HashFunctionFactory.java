package se.rosenbaum.iblt;

import se.rosenbaum.iblt.data.Data;

/**
 * User: kalle
 * Date: 9/11/14 7:24 PM
 */
public interface HashFunctionFactory<D extends Data> {
    public HashFunction<D> createHashFunction(int hashFunctionNumber);
}
