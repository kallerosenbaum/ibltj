package se.rosenbaum.iblt.hash;

import org.junit.Ignore;
import org.junit.Test;
import se.rosenbaum.iblt.data.IntegerData;
import se.rosenbaum.iblt.util.TestUtils;

import java.util.Random;

import static org.junit.Assert.assertTrue;
import static se.rosenbaum.iblt.util.TestUtils.data;

public class IntegerDataSubtablesHashFunctionsTest {
    @Test
    @Ignore
    public void test4and6() {
        IntegerDataSubtablesHashFunctions sut = new IntegerDataSubtablesHashFunctions(10, 2);
        int hash40 = sut.hash(0, data(4));
        int hash41 = sut.hash(1, data(4));

        int hash60 = sut.hash(0, data(6));
        int hash61 = sut.hash(1, data(6));

        assertTrue(hash40 != hash60 || hash41 != hash61);
    }

    @Test
    public void testDistributionSingleFunction() {
        testDistribution(1);
    }

    private void testDistribution(int hashFunctionCount) {
        IntegerDataSubtablesHashFunctions sut = new IntegerDataSubtablesHashFunctions(10, hashFunctionCount);
        int[] result = new int[10];
        Random random = new Random();
        for (int i = 0; i < 1000000; i++) {
            IntegerData data = TestUtils.data(random.nextInt());
            for (int j = 0; j < hashFunctionCount; j++) {
                result[sut.hash(j, data)]++;
            }
        }
        for (int i = 0; i < 10; i++) {
            assertTrue(90000*hashFunctionCount < result[i]);
        }
    }

    @Test
    public void testDistributionTwoFunctions() {
        testDistribution(2);
    }
}
