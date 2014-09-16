package se.rosenbaum.iblt.util;

import se.rosenbaum.iblt.Cell;
import se.rosenbaum.iblt.data.IntegerData;

/**
 * Created with IntelliJ IDEA.
 * User: kalle
 * Date: 9/16/14
 * Time: 9:19 PM
 * To change this template use File | Settings | File Templates.
 */
public class TestUtils {
    public static Cell[] createIntegerCells(int numberOfCells) {
        Cell[] cells = new Cell[numberOfCells];
        for (int i = 0; i < numberOfCells; i++) {
            cells[i] = new Cell(new IntegerData(0), new IntegerData(0));
        }
        return cells;
    }
}
