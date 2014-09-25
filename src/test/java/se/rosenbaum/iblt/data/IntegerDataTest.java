package se.rosenbaum.iblt.data;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class IntegerDataTest {

    @Test
    public void testOverflowPositive() {
        IntegerData sut = new IntegerData(Integer.MAX_VALUE);
        sut.add(new IntegerData(1));
        sut.subtract(new IntegerData(1));
        assertEquals(Integer.MAX_VALUE, sut.getValue());
    }

    @Test
    public void testOverflowNegative() {
        IntegerData sut = new IntegerData(Integer.MIN_VALUE);
        sut.add(new IntegerData(-1));
        sut.subtract(new IntegerData(-1));
        assertEquals(Integer.MIN_VALUE, sut.getValue());
    }

    @Test
    public void testOverflowNegativeTwice() {
        IntegerData sut = data(Integer.MIN_VALUE);
        sut.add(data(Integer.MIN_VALUE + 1));
        sut.add(data(Integer.MIN_VALUE + 2));

        sut.subtract(data(Integer.MIN_VALUE + 1));
        sut.subtract(data(Integer.MIN_VALUE));
        assertEquals(Integer.MIN_VALUE + 2, sut.getValue());
    }

    private IntegerData data(int value) {
        return new IntegerData(value);
    }
}
