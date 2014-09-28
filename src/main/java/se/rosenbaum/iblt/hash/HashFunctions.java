package se.rosenbaum.iblt.hash;

import se.rosenbaum.iblt.data.Data;

public interface HashFunctions<D extends Data> {
    public int getHashFunctionCount();
    public int hash(int hashFunctionNumber, D data);
}
