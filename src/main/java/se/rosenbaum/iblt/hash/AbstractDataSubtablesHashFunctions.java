package se.rosenbaum.iblt.hash;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import se.rosenbaum.iblt.data.Data;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public abstract class AbstractDataSubtablesHashFunctions<D extends Data> implements HashFunctions<D> {
    protected int cellCount;
    protected int hashFunctionCount;
    protected MessageDigest messageDigest;
    protected HashFunction hashingImplementation;

    public AbstractDataSubtablesHashFunctions(int cellCount, int hashFunctionCount) {
        if (cellCount % hashFunctionCount != 0) {
            throw new RuntimeException("Number of cells must be a multiple of number of hash functions! cellCount=" +
            cellCount + ", hashFunctionCount=" + hashFunctionCount);
        }
        try {
            hashingImplementation = Hashing.murmur3_32();
            messageDigest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Could not create MessageDigest SHA-256", e);
        }
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
//        System.out.println("h#:" + hashFunctionNumber + ", data: " + data.getValue() + ", cellIndex: " + cellIndex);
        return cellIndex;
    }

    public abstract int digest(int hashFunctionNumber, D data);
}
