package se.rosenbaum.iblt.hash;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import se.rosenbaum.iblt.data.Data;

public abstract class AbstractDataSubtablesHashFunctions<D extends Data> implements HashFunctions<D> {
    protected int cellCount;
    protected int hashFunctionCount;
    protected HashFunction hashingImplementation;

    public AbstractDataSubtablesHashFunctions(int cellCount, int hashFunctionCount) {
        if (cellCount % hashFunctionCount != 0) {
            throw new RuntimeException("Number of cells must be a multiple of number of hash functions! cellCount=" +
            cellCount + ", hashFunctionCount=" + hashFunctionCount);
        }
        hashingImplementation = Hashing.murmur3_32();
        this.hashFunctionCount = hashFunctionCount;
        this.cellCount = cellCount;
    }

    public int getHashFunctionCount() {
        return hashFunctionCount;
    }

    public int hash(int hashFunctionNumber, D data) {
        // hashFunctionNumber acts as a salt so that the different
        // hashFunctions yields different results
        int digest = digest(hashFunctionNumber, data);

        int subtableSize = cellCount / hashFunctionCount;
        // We want a positive integer. Note that this is not very good because zero has
        // half the probability as the rest of the values. -2 and 2 will both result i 2
        // but only 0 will result in 0.
        int cellIndex = Math.abs(digest) % subtableSize + hashFunctionNumber * subtableSize;
        return cellIndex;
    }

    public abstract int digest(int hashFunctionNumber, D data);
}
