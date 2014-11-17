package se.rosenbaum.iblt.hash;

import com.google.common.hash.HashCode;
import se.rosenbaum.iblt.data.IntegerData;

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
        HashCode hashCode = hashingImplementation.hashInt(integerToDigest);
        return hashCode.asInt();
    }
}
