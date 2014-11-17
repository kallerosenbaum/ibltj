package se.rosenbaum.iblt.hash;

import com.google.common.hash.HashCode;
import se.rosenbaum.iblt.data.ByteArrayData;

public class ByteArraySubtablesHashFunctions<D extends ByteArrayData> extends AbstractDataSubtablesHashFunctions<D> {

    public ByteArraySubtablesHashFunctions(int cellCount, int hashFunctionCount) {
        super(cellCount, hashFunctionCount);
    }

    @Override
    public int digest(int hashFunctionNumber, ByteArrayData data) {
        byte[] valueToDigest = data.getValue();
        valueToDigest[0] += hashFunctionNumber;

        HashCode hashCode = hashingImplementation.hashBytes(valueToDigest);
        return hashCode.asInt();
    }
}
