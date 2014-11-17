package se.rosenbaum.iblt.data;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class ByteArrayDataTest {
    ByteArrayData sut;

    @Before
    public void setup() {
        sut = new ByteArrayData(4);
    }

    @Test
    public void testDefaultZero() {
        assertArrayEquals(bytes(0,0,0,0), sut.getValue());
    }

    @Test
    public void testAddZero() {
        byte[] expected = sut.getValue();
        sut.add(new ByteArrayData(4));
        assertArrayEquals(expected, sut.getValue());
    }
    @Test
    public void testAddSingle() {
        ByteArrayData expected = data(1, 2, 3, 4);
        sut.add(expected);
        assertArrayEquals(expected.getValue(), sut.getValue());
    }
    @Test
    public void testSubtractSingle() {
        ByteArrayData expected = data(1, 2, 3, 4);
        sut.subtract(expected);
        assertArrayEquals(expected.getValue(), sut.invertCopy().getValue());
    }

    @Test
    public void testEqualsZero() {
        assertArrayEquals(new ByteArrayData(4).getValue(), sut.getValue());
    }

    @Test
    public void testEquals() {
        ByteArrayData equal = data(1, 2, 3, 4);
        sut.add(equal);
        assertEquals(equal, sut);
    }

    @Test
    public void testNotEquals() {
        ByteArrayData equal = data(1, 2, 3, 4);
        sut.add(equal);
        sut.add(data(5, 6, 7, 8));
        assertFalse(equal.equals(sut));
    }

    public ByteArrayData data(int... bytes) {
        return new ByteArrayData(bytes(bytes));
    }

    private byte[] bytes(int... bytes) {
        byte[] value = new byte[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            value[i] = (byte)bytes[i];
        }
        return value;
    }
}
