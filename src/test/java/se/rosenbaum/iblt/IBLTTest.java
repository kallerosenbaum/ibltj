package se.rosenbaum.iblt;

import org.junit.Before;
import org.junit.Test;
import se.rosenbaum.iblt.data.IntegerData;
import se.rosenbaum.iblt.hash.IntegerDataSubtablesHashFunction;
import se.rosenbaum.iblt.util.TestUtils;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

/**
 * User: kalle
 * Date: 9/10/14 9:46 PM
 */
public class IBLTTest {
    public static final IntegerData ONE = new IntegerData(1);
    private IBLT<IntegerData, IntegerData> sut;

    @Before
    public void setup() {
        setup(1000, 5);
    }

    private void setup(int cellCount, int hashFunctionCount) {
        sut = new IBLT<IntegerData, IntegerData>(TestUtils.createIntegerCells(cellCount), new IntegerDataSubtablesHashFunction(cellCount, hashFunctionCount));
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

}
