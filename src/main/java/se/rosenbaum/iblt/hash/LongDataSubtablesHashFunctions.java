package se.rosenbaum.iblt.hash;

import se.rosenbaum.iblt.data.LongData;

import java.nio.ByteBuffer;

/**
* User: kalle
* Date: 9/14/14 1:21 PM
*/
public class LongDataSubtablesHashFunctions extends AbstractDataSubtablesHashFunctions<LongData> {

    public LongDataSubtablesHashFunctions(int cellCount, int hashFunctionCount) {
        super(hashFunctionCount, cellCount);
    }

    @Override
    public int digest(int hashFunctionNumber, LongData data) {
        long longToDigest = data.getValue() + hashFunctionNumber;

        byte[] longBytes = ByteBuffer.allocate(8).putLong(longToDigest).array();
        byte[] digested = messageDigest.digest(longBytes);
        return ByteBuffer.allocate(4).put(digested, 28, 4).getInt(0);
    }
}
