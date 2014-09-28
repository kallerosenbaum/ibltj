package se.rosenbaum.iblt.data;

/**
 * User: kalle
 * Date: 9/11/14 6:51 PM
 */
public class LongData implements Data<LongData> {
    private long data;

    public LongData(long data) {
        this.data = data;
    }

    @Override
    public void add(LongData dataToAdd) {
        this.data += dataToAdd.data;
    }

    @Override
    public void subtract(LongData dataToSubtract) {
        this.data -= dataToSubtract.data;
    }

    @Override
    public boolean isZero() {
        return data == 0;
    }

    @Override
    public LongData invertCopy() {
        return new LongData(-this.data);
    }

    @Override
    public LongData copy() {
        return new LongData(this.data);
    }

    @Override
    public boolean equals(Object other) {
        return other != null &&
                other instanceof LongData &&
                data == ((LongData) other).data;
    }

    @Override
    public int hashCode() {
        return Long.valueOf(data).hashCode();
    }

    @Override
    public String toString() {
        return "" + data;
    }

    public long getValue() {
        return data;
    }

}
