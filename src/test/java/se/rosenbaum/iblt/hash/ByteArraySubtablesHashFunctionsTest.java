package se.rosenbaum.iblt.hash;

import org.junit.Test;
import se.rosenbaum.iblt.data.ByteArrayData;

import static org.junit.Assert.*;

public class ByteArraySubtablesHashFunctionsTest {
    @Test
    public void testBruteForceNegativeCellIndex() {
        ByteArraySubtablesHashFunctions<ByteArrayData> hashFunctions = new ByteArraySubtablesHashFunctions<ByteArrayData>(10749, 3) {
            @Override
            public int digest(int hashFunctionNumber, ByteArrayData data) {
                return Integer.MIN_VALUE;
            }
        };
        System.out.println("Integer.MIN_VALUE: " + Integer.MIN_VALUE);
        System.out.println("abs(Integer.MIN_VALUE) " + Math.abs(Integer.MIN_VALUE));
        // At one point the hashFunctions.hash(hashFunctionNumber, data) returned -1849 when cellCount was 10749.
        // This avfter running several millions of hashes successfully.
        for (int i = 0; i < 3; i++) {
            int hash = hashFunctions.hash(i, new ByteArrayData(new byte[]{1}));
            assertEquals(i*(10749/3), hash);
        }
    }
}