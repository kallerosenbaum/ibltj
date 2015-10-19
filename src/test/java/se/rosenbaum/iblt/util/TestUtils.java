package se.rosenbaum.iblt.util;

import se.rosenbaum.iblt.Cell;
import se.rosenbaum.iblt.IBLT;
import se.rosenbaum.iblt.data.IntegerData;
import se.rosenbaum.iblt.hash.HashFunction;
import se.rosenbaum.iblt.hash.IntegerDataHashFunction;
import se.rosenbaum.iblt.hash.IntegerDataSubtablesHashFunctions;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

public class TestUtils {
    public static Cell<IntegerData, IntegerData>[] createIntegerCells(int numberOfCells) {
        Cell[] cells = new Cell[numberOfCells];
        HashFunction<IntegerData, IntegerData> hashFunction = new IntegerDataHashFunction();
        for (int i = 0; i < numberOfCells; i++) {
            cells[i] = new Cell(data(0), data(0), data(0), hashFunction, 0);
        }
        return cells;
    }

    public static Map<IntegerData,IntegerData> createRandomMap(int size) {
        Random random = new Random(1);
        return createRandomMap(size, random);
    }

    public static Map<IntegerData,IntegerData> createRandomMap(int size, Random random) {
        Map<IntegerData, IntegerData> result = new HashMap<IntegerData, IntegerData>();

        for (int i = 0; i < size; i++)
            while (result.put(data(random.nextInt()), data(random.nextInt())) != null) {
            }
        return result;
    }

    public static Map<IntegerData, IntegerData> createDifference(int missingInIbltCount, int extraInIbltCount, Map<IntegerData, IntegerData> ibltData) {
        Random random = new Random(2);
        return createDifference(missingInIbltCount, extraInIbltCount, ibltData, random);
    }

    public static Map<IntegerData, IntegerData> createDifference(int missingInIbltCount, int extraInIbltCount, Map<IntegerData, IntegerData> ibltData, Random random) {
        Map<IntegerData, IntegerData> result = new HashMap<IntegerData, IntegerData>(ibltData);

        Iterator<IntegerData> dataToRemove = ibltData.keySet().iterator();
        for (int i = 0; i < extraInIbltCount; i++) {
            result.remove(dataToRemove.next());
        }
        for (int i = 0; i < missingInIbltCount; i++) {
            while (true) {
                IntegerData key = data(random.nextInt());
                if (!ibltData.containsKey(key)) {
                    result.put(key, data(random.nextInt()));
                    break;
                }
            }
        }
        return result;
    }

    public static Map<IntegerData,IntegerData> createMap(int... data) {
        Map<IntegerData, IntegerData> result = new HashMap<IntegerData, IntegerData>();
        for (int i : data) {
            result.put(data(i), data(i));
        }
        return result;
    }


    public static IntegerData data(int value) {
        return new IntegerData(value);
    }

    public static IBLT<IntegerData, IntegerData> createIblt(int cellCount, int hashFunctionCount) {
        return new IBLT<IntegerData, IntegerData>(createIntegerCells(cellCount),
                new IntegerDataSubtablesHashFunctions(cellCount, hashFunctionCount));
    }
}
