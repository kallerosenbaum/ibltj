package se.rosenbaum.iblt.hash;

import com.google.common.hash.HashCode;
import se.rosenbaum.iblt.data.LongData;

/**
* User: kalle
* Date: 9/14/14 1:21 PM
*/
public class LongDataSubtablesHashFunctions<D extends LongData> extends AbstractDataSubtablesHashFunctions<D> {

    public LongDataSubtablesHashFunctions(int cellCount, int hashFunctionCount) {
        super(cellCount, hashFunctionCount);
    }

    @Override
    public int digest(int hashFunctionNumber, LongData data) {
        long longToDigest = data.getValue() + hashFunctionNumber;

        HashCode hashCode = hashingImplementation.hashLong(longToDigest);
        return hashCode.asInt();
    }
}
