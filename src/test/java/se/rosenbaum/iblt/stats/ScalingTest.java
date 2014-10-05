package se.rosenbaum.iblt.stats;

import org.junit.Ignore;
import org.junit.Test;
import se.rosenbaum.iblt.IBLT;
import se.rosenbaum.iblt.data.IntegerData;
import se.rosenbaum.iblt.util.SetReconciliator;
import se.rosenbaum.iblt.util.TestUtils;

import java.text.Format;
import java.util.Map;
import java.util.Random;

import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import static se.rosenbaum.iblt.util.TestUtils.createIblt;
import static se.rosenbaum.iblt.util.TestUtils.data;

public class ScalingTest {

    public static class IBLTSpec {
        int dataSize;
        int cellCount;
        int hashFunctionCount;
        int missingCount;
        int extraCount;
    }

    public static class TestResult {
        IBLTSpec spec;
        boolean success;

        public String toString() {
            return "Data size: " + spec.dataSize + ", cellCount: " + spec.cellCount + ", hashFunctionCount: " +
                    spec.hashFunctionCount + ", missingCount: " + spec.missingCount + ",  extraCount: " +
                    spec.extraCount + ", Result: " + (success ? "Success" : "Fail");
        }
    }



    @Test
    @Ignore
    public void test() {
        Random random = new Random(System.currentTimeMillis());
        IBLTSpec spec = new IBLTSpec();
        TestResult result;

        for (int cellCount = 1; cellCount <= 100000; cellCount *= 10) {
            spec.cellCount = cellCount;
            for (int hashFunctionCount = 1; hashFunctionCount <= cellCount/10; hashFunctionCount *= 10) {
                spec.hashFunctionCount = hashFunctionCount;
                nextHashFunctionCount:
                for (int diffs = 0; diffs <= cellCount ; diffs = (diffs == 0 ? 1 : diffs*2)) {
                    spec.missingCount = diffs;
                    spec.extraCount = diffs;
                    for (int dataSize = cellCount; dataSize < 100000; dataSize *= 10) {
                        spec.dataSize = dataSize;
                        result = testReconciliation(spec, random);
                        logResult(result);
                        if (!result.success) {
                            break nextHashFunctionCount;
                        }
                    }
                }
            }
        }
    }

    private void logResult(TestResult result) {
        IBLTSpec spec = result.spec;
        System.out.printf("%s,%s,%s,%s,%s,%s\n", spec.cellCount, spec.hashFunctionCount, spec.missingCount,
                spec.extraCount, spec.dataSize, result.success ? "SUCCESS" : "FAIL");
    }

    public TestResult testReconciliation(IBLTSpec spec, Random random) {
        Map<IntegerData,IntegerData> ibltData = TestUtils.createRandomMap(spec.dataSize, random);
        Map<IntegerData, IntegerData> myData = TestUtils.createDifference(spec.missingCount, spec.extraCount, ibltData, random);
        IBLT<IntegerData, IntegerData> iblt = createIblt(spec.cellCount, spec.hashFunctionCount);

        Map<IntegerData, IntegerData> result = reconcile(ibltData, iblt, myData);

        TestResult testResult = new TestResult();
        testResult.spec = spec;
        testResult.success = result != null;
        return testResult;
    }


    private Map<IntegerData, IntegerData> reconcile(Map<IntegerData, IntegerData> ibltData, IBLT<IntegerData, IntegerData> iblt, Map<IntegerData, IntegerData> myDataMap) {
        for (Map.Entry<IntegerData, IntegerData> ibltEntry : ibltData.entrySet()) {
            iblt.insert(ibltEntry.getKey(), ibltEntry.getValue());
        }

        SetReconciliator<IntegerData, IntegerData> reconciliator = new SetReconciliator<IntegerData, IntegerData>(iblt);
        Map<IntegerData, IntegerData> result = reconciliator.reconcile(myDataMap);

        if (result == null) {
            return null;
        }
        assertEquals(ibltData.size(), result.size());
        for (Map.Entry<IntegerData, IntegerData> resultEntry : result.entrySet()) {
            IntegerData expectedValue = ibltData.remove(resultEntry.getKey());
            assertNotNull(expectedValue);
            assertEquals(expectedValue, resultEntry.getValue());
        }
        assertEquals(0, ibltData.size());
        return result;
    }

}

