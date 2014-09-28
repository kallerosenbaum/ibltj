package se.rosenbaum.iblt;

import org.junit.Test;
import se.rosenbaum.iblt.data.IntegerData;
import se.rosenbaum.iblt.util.SetReconciliator;

import java.util.Map;
import java.util.Random;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static org.junit.Assert.assertEquals;
import static se.rosenbaum.iblt.util.TestUtils.*;

public class SetReconciliatorTest {

    @Test
    public void testReconcileCannotListEntries() {
        IBLT<IntegerData, IntegerData> iblt = createIblt(1, 1);

        for (Map.Entry<IntegerData, IntegerData> ibltEntry : createMap(1, 2).entrySet()) {
            iblt.insert(ibltEntry.getKey(), ibltEntry.getValue());
        }

        SetReconciliator<IntegerData, IntegerData> reconciliator = new SetReconciliator<IntegerData, IntegerData>(iblt);
        Map<IntegerData, IntegerData> result = reconciliator.reconcile(createMap(3));
        assertNull(result);
    }

    @Test
    public void testReconcile() throws Exception {
        IBLT<IntegerData, IntegerData> iblt = createIblt(10, 2);
        testReconcile(createMap(1, 2, 4, 5, 6, 8, 9), iblt, createMap(1, 3, 4, 5, 6, 8, 9, 10));
    }

    @Test
    public void testReconcile5() {
        IBLT<IntegerData, IntegerData> iblt = createIblt(10, 2);
        Map<IntegerData, IntegerData> ibltData = createRandomMap(5);
        Map<IntegerData, IntegerData> myData = createDifference(1, 1, ibltData);
        testReconcile(ibltData, iblt, myData);
    }

    @Test
    public void testReconcile1000() {
        IBLT<IntegerData, IntegerData> iblt = createIblt(200, 4);
        Map<IntegerData, IntegerData> ibltData = createRandomMap(1000);
        Map<IntegerData, IntegerData> myData = createDifference(50, 50, ibltData);
        testReconcile(ibltData, iblt, myData);
    }

    @Test
    public void testReconcile100() {
        IBLT<IntegerData, IntegerData> iblt = createIblt(16, 4);
        Map<IntegerData, IntegerData> ibltData = createRandomMap(100);
        Map<IntegerData, IntegerData> myData = createDifference(5, 5, ibltData);
        testReconcile(ibltData, iblt, myData);
    }

    @Test
    public void testReconcile10() {
        IBLT<IntegerData, IntegerData> iblt = createIblt(20, 5);
        Map<IntegerData, IntegerData> ibltData = createRandomMap(10);
        Map<IntegerData, IntegerData> myData = createDifference(5, 5, ibltData);
        testReconcile(ibltData, iblt, myData);
    }

    @Test
    public void testReconcile1() {
        IBLT<IntegerData, IntegerData> iblt = createIblt(10, 2);
        Map<IntegerData, IntegerData> ibltData = createMap(1);
        Map<IntegerData, IntegerData> myData = createMap(2, 3, 4, 7);
        testReconcile(ibltData, iblt, myData);
    }

    private void  testReconcile(Map<IntegerData, IntegerData> ibltData, IBLT<IntegerData, IntegerData> iblt, Map<IntegerData, IntegerData> myDataMap) {
        for (Map.Entry<IntegerData, IntegerData> ibltEntry : ibltData.entrySet()) {
            iblt.insert(ibltEntry.getKey(), ibltEntry.getValue());
        }

        SetReconciliator<IntegerData, IntegerData> reconciliator = new SetReconciliator<IntegerData, IntegerData>(iblt);
        Map<IntegerData, IntegerData> result = reconciliator.reconcile(myDataMap);

        assertEquals(ibltData.size(), result.size());
        for (Map.Entry<IntegerData, IntegerData> resultEntry : result.entrySet()) {
            IntegerData expectedValue = ibltData.remove(resultEntry.getKey());
            assertNotNull(expectedValue);
            assertEquals(expectedValue, resultEntry.getValue());
        }
        assertEquals(0, ibltData.size());
    }

}
