package se.rosenbaum.iblt;

import org.junit.Before;
import org.junit.Test;
import se.rosenbaum.iblt.data.IntegerData;
import se.rosenbaum.iblt.util.TestUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static se.rosenbaum.iblt.util.TestUtils.data;

public class CellTest {
    private Cell<IntegerData, IntegerData> sut;

    @Before
    public void setup() {
        sut = TestUtils.createIntegerCells(1)[0];
    }

    @Test(expected = NullPointerException.class)
    public void testAddNullValue() {
        sut.add(data(1), null);
    }

    @Test(expected = NullPointerException.class)
    public void testAddNullKey() {
        sut.add(null, data(1));
    }

    @Test(expected = NullPointerException.class)
    public void testSubtractNullKey() {
        sut.subtract(null, data(1));
    }

    @Test(expected = NullPointerException.class)
    public void testSubtractNullValue() {
        sut.subtract(data(1), null);
    }


    @Test
    public void testIsUnambiguousExtraneousDeleteCount1() {
        sut.add(data(1), data(10));
        sut.add(data(2), data(20));
        sut.subtract(data(3), data(30));
        // keySum==0 and valueSum==0
        assertEquals(1, sut.getCount());
        assertFalse(sut.isUnambiguous());
    }

    @Test
    public void testIsUnambiguousExtraneousDeleteCount0() {
        sut.add(data(1), data(10));
        sut.subtract(data(3), data(30));
        assertEquals(0, sut.getCount());
        assertFalse(sut.isUnambiguous());
    }


    @Test
    public void testIsUnambiguousExtraneousDeleteCountMinus1() {
        sut.subtract(data(3), data(30));
        sut.subtract(data(4), data(40));
        sut.add(data(1), data(10));
        assertEquals(-1, sut.getCount());
        assertFalse(sut.isUnambiguous());
    }

    @Test
    public void testIsUnambiguousCount1() {
        sut.add(data(3), data(30));
        sut.add(data(4), data(40));
        sut.subtract(data(3), data(30));
        assertEquals(1, sut.getCount());
        assertTrue(sut.isUnambiguous());
    }

    @Test
    public void testIsUnambiguousCount0() {
        sut.add(data(3), data(30));
        sut.add(data(4), data(40));
        sut.subtract(data(4), data(40));
        sut.subtract(data(3), data(30));
        assertEquals(0, sut.getCount());
        assertTrue(sut.isUnambiguous());
    }


    @Test
    public void testIsUnambiguousCountMinus1() {
        sut.add(data(3), data(30));
        sut.add(data(4), data(40));
        sut.subtract(data(4), data(40));
        sut.subtract(data(3), data(30));
        sut.subtract(data(1), data(10));
        assertEquals(-1, sut.getCount());
        assertTrue(sut.isUnambiguous());
    }
}
