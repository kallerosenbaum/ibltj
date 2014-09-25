package se.rosenbaum.iblt;

import org.junit.Before;
import org.junit.Test;
import se.rosenbaum.iblt.data.IntegerData;
import se.rosenbaum.iblt.exception.WrongCellIndexException;
import se.rosenbaum.iblt.hash.IntegerDataSubtablesHashFunctions;
import se.rosenbaum.iblt.hash.IntegerSimpleHashFunctions;
import se.rosenbaum.iblt.util.ResidualData;
import se.rosenbaum.iblt.util.TestUtils;

import java.util.Map;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static se.rosenbaum.iblt.util.TestUtils.data;

public class IBLTTest {
    public static final IntegerData ONE = new IntegerData(1);
    private IBLT<IntegerData, IntegerData> sut;

    @Before
    public void setup() {
        setup(1000, 5);
    }

    private void setup(int cellCount, int hashFunctionCount) {
        sut = new IBLT<IntegerData, IntegerData>(TestUtils.createIntegerCells(cellCount), new IntegerDataSubtablesHashFunctions(cellCount, hashFunctionCount));
    }

    private void insert(int key, int value) {
        sut.insert(new IntegerData(key), new IntegerData(value));
    }

    private int get(int key) throws NotFoundException {
        IntegerData value = sut.get(new IntegerData(key));
        assertNotNull(value);
        return value.getValue();
    }

    private void delete(int key, int value) {
        sut.delete(new IntegerData(key), new IntegerData(value));
    }

    @Test
    public void testInsertOne() throws Exception {
        insert(1, 11);
        assertEquals(11, get(1));
    }

    @Test
    public void testInsert2() {
        testInsert(2);
    }

    @Test
    public void testInsert100() {
        testInsert(100);
    }

    @Test
    public void testKeys15and16() {
        insert(15, 1);
        insert(16, 2);
        try {
            assertEquals(1, get(15));
            delete(15, 1);
            assertEquals(2, get(16));
        } catch (NotFoundException e) {
            fail("NotFoundException for key " + ((IntegerData)e.getKey()).getValue());
        }
    }

    private void testInsert(int keyCount) {
        int constant = 823476387;
        for (int i = 1; i <= keyCount; i++) {
            insert(i, i + constant);
        }
        for (int i = 1; i <= keyCount; i++) {
            IntegerData key = new IntegerData(i);
            IntegerData value = null;
            try {
                value = sut.get(key);
                assertNotNull(value);
                assertEquals("Failed on key " + i, i+constant, value.getValue());
                sut.delete(key, value);
            } catch (NotFoundException e) {
                fail("NotFoundException for key " + i);
            }
        }
    }

    @Test(expected = NotFoundException.class)
    public void testInsertDuplicate() throws NotFoundException {
        insert(1, 11);
        insert(1, 11);
        sut.get(ONE);
    }

    @Test
    public void testInsertDelete() throws NotFoundException {
        insert(1, 11);
        delete(1, 11);
        assertNull(sut.get(ONE));
    }

    @Test
    public void testListEntriesEmpty() {
        ResidualData<IntegerData,IntegerData> residualData = sut.listEntries();
        assertNotNull(residualData.getAbsentEntries());
        assertNotNull(residualData.getExtraEntries());
        assertEquals(0, residualData.getAbsentEntries().size());
        assertEquals(0, residualData.getExtraEntries().size());
    }

    @Test(expected = WrongCellIndexException.class)
    public void testHashFunctionCellIndexOutOfBounds() {
        sut = new IBLT<IntegerData, IntegerData>(TestUtils.createIntegerCells(1), new IntegerSimpleHashFunctions(2, 1));
        insert(0, 0);
        insert(1, 1); // This will give a cell index > cellCount-1
    }

    @Test(expected = WrongCellIndexException.class)
    public void testHashFunctionCellIndexOutOfBoundsOnDelete() {
        sut = new IBLT<IntegerData, IntegerData>(TestUtils.createIntegerCells(1), new IntegerSimpleHashFunctions(2, 1));
        delete(0, 0);
        delete(1, 1); // This will give a cell index > cellCount-1
    }

    @Test
    public void testListEntriesExtraneousInserts() {
        sut = new IBLT<IntegerData, IntegerData>(TestUtils.createIntegerCells(1), new IntegerDataSubtablesHashFunctions(1, 1));
        insertAll(1, 2);
        // the only one cell contains more than one element. Therfore the IBLT cannot list its entries.
        assertNull(sut.listEntries());
    }

    @Test
    public void testListEntriesTooManyEntries() {
        sut = new IBLT<IntegerData, IntegerData>(TestUtils.createIntegerCells(5), new IntegerDataSubtablesHashFunctions(5, 1));
        insertAll(1, 2, 3, 4, 5, 6);
        // At least one cell contains more than one element. Therfore the IBLT cannot list its entries.
        assertNull(sut.listEntries());
    }

    @Test
    public void testListEntriesAllEntriesExactlyFit() {
        sut = new IBLT<IntegerData, IntegerData>(TestUtils.createIntegerCells(5), new IntegerSimpleHashFunctions(5, 1));
        insertAll(1, 2, 3, 4, 5);
        // All the values will be in a separate cell, since we use the IntegerSimpleHashFunctions. We will be able to
        ResidualData<IntegerData, IntegerData> result = sut.listEntries();
        assertNotNull(result);
        assertEquals(0, result.getAbsentEntries().size());
        Map<IntegerData,IntegerData> extras = result.getExtraEntries();
        assertAllIncluded(extras, 1, 2, 3, 4, 5);
    }

    @Test
    public void testListEntriesOnlyDeletes() {
        sut = new IBLT<IntegerData, IntegerData>(TestUtils.createIntegerCells(5), new IntegerSimpleHashFunctions(5, 1));
        deleteAll(1, 2, 3, 4, 5);
        // All the values will be in a separate cell, since we use the IntegerSimpleHashFunctions. We will be able to
        ResidualData<IntegerData, IntegerData> result = sut.listEntries();
        assertNotNull(result);
        assertEquals(0, result.getExtraEntries().size());
        Map<IntegerData,IntegerData> absents = result.getAbsentEntries();
        assertAllIncluded(absents, 1, 2, 3, 4, 5);
    }

    @Test
    public void testListEntriesMixed() {
        sut = new IBLT<IntegerData, IntegerData>(TestUtils.createIntegerCells(5), new IntegerSimpleHashFunctions(5, 1));
        insertAll(1, 3, 4);
        deleteAll(2, 5);
        // All the values will be in a separate cell, since we use the IntegerSimpleHashFunctions. We will be able to
        ResidualData<IntegerData, IntegerData> result = sut.listEntries();
        assertNotNull(result);
        Map<IntegerData, IntegerData> extras = result.getExtraEntries();
        assertAllIncluded(extras, 1, 3, 4);
        Map<IntegerData,IntegerData> absents = result.getAbsentEntries();
        assertAllIncluded(absents, 2, 5);
    }

    private void assertAllIncluded(Map<IntegerData, IntegerData> map, int... values) {
        assertEquals(values.length, map.size());
        for (int value : values) {
            assertTrue(map.containsKey(data(value)));
            assertEquals(data(value), map.get(data(value)));

        }
    }

    private void insertAll(int... values) {
        for (int value : values) {
            insert(value, value);
        }
    }

    private void deleteAll(int... values) {
        for (int value : values) {
            delete(value, value);
        }
    }
}
