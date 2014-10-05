package se.rosenbaum.iblt.data;

import java.util.Arrays;

public class ByteArrayData implements Data<ByteArrayData> {
    private byte[] byteArray;
    private static final byte ZERO = 0;

    public ByteArrayData(int sizeInBytes) {
        this.byteArray = new byte[sizeInBytes];
    }

    public ByteArrayData(byte[] bytes) {
        this.byteArray = bytes;
    }

    @Override
    public void add(ByteArrayData dataToAdd) {
        for (int i = 0; i < byteArray.length; i++) {
            byteArray[i] ^= dataToAdd.byteArray[i];
        }
    }

    @Override
    public void subtract(ByteArrayData dataToSubtract) {
        add(dataToSubtract);
    }

    @Override
    public boolean isZero() {
        for (int i = 0; i < byteArray.length; i++) {
            if (byteArray[i] != ZERO) {
                return false;
            }
        }
        return true;
    }

    @Override
    public ByteArrayData invertCopy() {
        return copy();
    }

    @Override
    public ByteArrayData copy() {
        return new ByteArrayData(getValue());
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ByteArrayData)) {
            return false;
        }
        return Arrays.equals(byteArray, ((ByteArrayData)obj).byteArray);
    }

    public byte[] getValue() {
        return Arrays.copyOf(byteArray, byteArray.length);
    }
}