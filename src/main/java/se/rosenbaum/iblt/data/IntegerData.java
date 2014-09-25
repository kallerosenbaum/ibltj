package se.rosenbaum.iblt.data;

/**
 * User: kalle
 * Date: 9/11/14 6:51 PM
 */
public class IntegerData implements Data<IntegerData> {
    private int data;

    public IntegerData(int data) {
        this.data = data;
    }

    @Override
    public void add(IntegerData dataToAdd) {
        this.data += dataToAdd.data;
    }

    @Override
    public void subtract(IntegerData dataToSubtract) {
        this.data -= dataToSubtract.data;
    }

    @Override
    public boolean isZero() {
        return data == 0;
    }

    @Override
    public IntegerData invertCopy() {
        return new IntegerData(-this.data);
    }

    @Override
    public IntegerData copy() {
        return new IntegerData(this.data);
    }

    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (other instanceof IntegerData) {
            return data == ((IntegerData)other).data;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Integer.valueOf(data).hashCode();
    }

    public int getValue() {
        return data;
    }

    public String toString() {
        return "" + data;
    }
}
