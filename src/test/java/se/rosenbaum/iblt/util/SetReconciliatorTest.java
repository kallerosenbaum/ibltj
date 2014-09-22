package se.rosenbaum.iblt.util;

import org.junit.Test;
import se.rosenbaum.iblt.IBLT;
import se.rosenbaum.iblt.data.IntegerData;
import se.rosenbaum.iblt.hash.IntegerDataSubtablesHashFunction;
import se.rosenbaum.iblt.hash.IntegerSimpleHashFunction;

import java.util.*;

import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static se.rosenbaum.iblt.util.TestUtils.createIntegerCells;

public class SetReconciliatorTest {
    private static class ID extends IntegerData {
        public ID(int value) {
            super(value);
        }
    }

    @Test
    public void testReconcile() throws Exception {
        int cellCount = 10;
        IBLT<IntegerData, IntegerData> iblt = new IBLT<IntegerData, IntegerData>(createIntegerCells(cellCount),
                new IntegerSimpleHashFunction(cellCount, 2));
        int domainSize = 10;
        // IBLT-data should be all values between 1 and 10, but some are missing
        // My-data should also be all values between 1 and 10 but some are missing of which some are also missing in
        // IBLT-data.

        // IBLT contains 1,2,4,5,6,8,9   (missing 3, 7, 10)
        // My-data contains 1,3,4,5,6,8,9,10 (missing 2, 7)
        List<Integer> missingInIBLT = Arrays.asList(3, 7, 10);
        List<Integer> missingInMyData = Arrays.asList(2, 7);
        Map<IntegerData, IntegerData> myData = new HashMap<IntegerData, IntegerData>();
        for (int i = 1; i <= domainSize; i++) {
            if (!missingInIBLT.contains(i)) {
                iblt.insert(data(i), data(i));
            }
            if (!missingInMyData.contains(i)) {
                myData.put(data(i), data(i));
            }
        }

        SetReconciliator<IntegerData, IntegerData> reconciliator = new SetReconciliator<IntegerData, IntegerData>(iblt);
        Map<IntegerData, IntegerData> result = reconciliator.reconcile(myData);

        Map<IntegerData, IntegerData> expectedResult = createMap(1, 2, 4, 5, 6, 8, 9);
        assertEquals(expectedResult.size(), result.size());
        for (Map.Entry<IntegerData, IntegerData> resultEntry : result.entrySet()) {
            IntegerData expectedValue = expectedResult.remove(resultEntry.getKey());
            assertNotNull(expectedValue);
            assertEquals(expectedValue, resultEntry.getValue());

        }
        assertEquals(0, expectedResult.size());

    }

    private Map<IntegerData,IntegerData> createMap(int... data) {
        Map<IntegerData, IntegerData> result = new HashMap<IntegerData, IntegerData>();
        for (int i : data) {
            result.put(data(i), data(i));
        }
        return result;
    }


    private IntegerData data(int dataValue) {
        return new IntegerData(dataValue);
    }
}
