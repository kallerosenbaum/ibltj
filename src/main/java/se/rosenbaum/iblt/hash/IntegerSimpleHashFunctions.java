package se.rosenbaum.iblt.hash;

import se.rosenbaum.iblt.data.IntegerData;

/**
* User: kalle
* Date: 9/14/14 1:21 PM
*/
public class IntegerSimpleHashFunctions implements HashFunctions<IntegerData> {
    private int cellCount;
    private int hashFunctionCount;

    public IntegerSimpleHashFunctions(int cellCount, int hashFunctionCount) {
        if (hashFunctionCount < 1) {
            throw new RuntimeException("Invalid hashFunctionCount: " + hashFunctionCount + ". Must be a positive integer");
        }
        if (cellCount % hashFunctionCount != 0) {
            throw new RuntimeException("Number of cells must be a multiple of number of hash functions");
        }
        this.cellCount = cellCount;
        this.hashFunctionCount = hashFunctionCount;
    }

    @Override
    public int getHashFunctionCount() {
        return hashFunctionCount;
    }

    @Override
    public int hash(int hashFunctionNumber, IntegerData data) {
        int subtableSize = cellCount / hashFunctionCount;
        // We want a positive integer. Note that this is not very good because zero has
        // half the probablity as the rest of the values. -2 and 2 will both result i 2
        // but only 0 will result in 0.

        // Also if data x ends up in cell c and d, and if data y ends up in cell c we don't want it to end up in cell d
        // as well. At least not for all y that end up in cell c.
        int cellIndex = (hashFunctionNumber + Math.abs(data.getValue())) % subtableSize + hashFunctionNumber * subtableSize;
        System.out.println("h#:" + hashFunctionNumber + ", data: " + data.getValue() + ", cellIndex: " + cellIndex);
        return cellIndex;
    }
}
