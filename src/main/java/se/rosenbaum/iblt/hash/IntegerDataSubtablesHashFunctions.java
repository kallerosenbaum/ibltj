package se.rosenbaum.iblt.hash;

import se.rosenbaum.iblt.data.IntegerData;

import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
* User: kalle
* Date: 9/14/14 1:21 PM
*/
public class IntegerDataSubtablesHashFunctions implements HashFunctions<IntegerData> {
    private int cellCount;
    private int hashFunctionCount;
    private MessageDigest digest;

    public IntegerDataSubtablesHashFunctions(int cellCount, int hashFunctionCount) {
        if (cellCount % hashFunctionCount != 0) {
            throw new RuntimeException("Number of cells must be a multiple of number of hash functions");
        }
        this.cellCount = cellCount;
        this.hashFunctionCount = hashFunctionCount;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Could not create MessageDigest SHA-256", e);
        }
    }

    @Override
    public int getHashFunctionCount() {
        return hashFunctionCount;
    }

    @Override
    public int hash(int hashFunctionNumber, IntegerData data) {
        // hashFunctionNumber acts as a salt so that the different
        // hashFunctions yields different results

        digest.reset();
        Integer integerToDigest = new Integer(data.getValue() + hashFunctionNumber);

        byte[] intBytes = ByteBuffer.allocate(4).putInt(integerToDigest).array();

        byte[] digested = digest.digest(intBytes);


        int result = ByteBuffer.allocate(4).put(digested, 28, 4).getInt(0);

        int subtableSize = cellCount / hashFunctionCount;
        // We want a positive integer. Note that this is not very good because zero has
        // half the probablity as the rest of the values. -2 and 2 will both result i 2
        // but only 0 will result in 0.
        int cellIndex = Math.abs(result) % subtableSize + hashFunctionNumber * subtableSize;
        System.out.println("h#:" + hashFunctionNumber + ", data: " + data.getValue() + ", cellIndex: " + cellIndex);
        return cellIndex;
    }
}
