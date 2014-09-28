package se.rosenbaum.iblt.util;

import se.rosenbaum.iblt.Cell;
import se.rosenbaum.iblt.data.IntegerData;
import se.rosenbaum.iblt.hash.HashFunction;
import se.rosenbaum.iblt.hash.IntegerDataHashFunction;

public class TestUtils {
    public static Cell<IntegerData, IntegerData>[] createIntegerCells(int numberOfCells) {
        Cell[] cells = new Cell[numberOfCells];
        HashFunction<IntegerData, IntegerData> hashFunction = new IntegerDataHashFunction();
        for (int i = 0; i < numberOfCells; i++) {
            cells[i] = new Cell(data(0), data(0), data(0), hashFunction);
        }
        return cells;
    }

    public static IntegerData data(int value) {
        return new IntegerData(value);
    }
}
