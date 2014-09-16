package se.rosenbaum.iblt.util;

import org.junit.Test;
import se.rosenbaum.iblt.IBLT;
import se.rosenbaum.iblt.data.IntegerData;
import se.rosenbaum.iblt.hash.IntegerDataSubtablesHashFunction;

import java.util.*;

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
                new IntegerDataSubtablesHashFunction(cellCount, 2));
        int domainSize = 10;
        // IBLT-data should be all values between 1 and 10, but some are missing
        // My-data should also be all values between 1 and 10 but some are missing of which some are also missing in
        // IBLT-data.

        // IBLT contains 1,2,4,5,6,8,9   (missing 3, 7, 10)
        // My-data contains 1,3,4,5,6,8,9,10 (missing 2, 7)
        List<Integer> missingInIBLT = Arrays.asList(3, 7, 10);
        List<Integer> missingInMyData = Arrays.asList(2, 7);
        List<Tuple<IntegerData, IntegerData>> myData = new ArrayList<Tuple<IntegerData, IntegerData>>();
        for (int i = 1; i <= domainSize; i++) {
            if (!missingInIBLT.contains(i)) {
                iblt.insert(data(i), data(i));
            }
            if (!missingInMyData.contains(i)) {
                myData.add(tuple(i, i));
            }
        }

        SetReconciliator<IntegerData, IntegerData> reconciliator = new SetReconciliator<IntegerData, IntegerData>(iblt);
        Iterator<Tuple<IntegerData, IntegerData>> result = reconciliator.reconcile(myData.iterator());

        Collection<Tuple<IntegerData, IntegerData>> expectedResult = createTuples(1, 2, 4, 5, 6, 8, 9);
        while (result.hasNext()) {
            assertTrue(expectedResult.remove(result.next()));
        }
        assertEquals(0, expectedResult.size());

    }

    private Collection<Tuple<IntegerData, IntegerData>> createTuples(int... keys) {
        Collection<Tuple<IntegerData, IntegerData>> expectedResult = new ArrayList<Tuple<IntegerData, IntegerData>>();
        for (int key : keys) {
            expectedResult.add(tuple(key, key));
        }
        return expectedResult;
    }

    private Tuple<IntegerData, IntegerData> tuple(int key, int value) {
        return new Tuple<IntegerData, IntegerData>(new IntegerData(key), new IntegerData(value));
    }

    private IntegerData data(int dataValue) {
        return new IntegerData(dataValue);
    }
}
