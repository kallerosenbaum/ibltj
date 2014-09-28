package se.rosenbaum.iblt.hash;

import se.rosenbaum.iblt.data.IntegerData;

import java.nio.ByteBuffer;

/**
* User: kalle
* Date: 9/14/14 1:21 PM
*/
public class IntegerDataSubtablesHashFunctions extends AbstractDataSubtablesHashFunctions<IntegerData> {

    public IntegerDataSubtablesHashFunctions(int cellCount, int hashFunctionCount) {
        super(cellCount, hashFunctionCount);
    }

    @Override
    public int digest(int hashFunctionNumber, IntegerData data) {
        int integerToDigest = data.getValue() + hashFunctionNumber;

        byte[] intBytes = ByteBuffer.allocate(4).putInt(integerToDigest).array();
        byte[] digested = messageDigest.digest(intBytes);
        return ByteBuffer.allocate(4).put(digested, 28, 4).getInt(0);
    }
}
