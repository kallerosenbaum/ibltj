package se.rosenbaum.iblt.hash;

import com.google.common.hash.HashCode;
import se.rosenbaum.iblt.data.ByteArrayData;

import java.nio.ByteBuffer;

public class ByteArraySubtablesHashFunctions extends AbstractDataSubtablesHashFunctions<ByteArrayData> {

    public ByteArraySubtablesHashFunctions(int cellCount, int hashFunctionCount) {
        super(cellCount, hashFunctionCount);
    }

    @Override
    public int digest(int hashFunctionNumber, ByteArrayData data) {
        byte[] valueToDigest = data.getValue();
        valueToDigest[0] += hashFunctionNumber;

        HashCode hashCode = hashingImplementation.hashBytes(valueToDigest);
        //byte[] digested = messageDigest.digest(valueToDigest);
        return hashCode.asInt();
                //ByteBuffer.allocate(4).put(digested, 28, 4).getInt(0);
    }
}
