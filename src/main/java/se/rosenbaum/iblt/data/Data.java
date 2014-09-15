package se.rosenbaum.iblt.data;

/**
 * User: kalle
 * Date: 9/11/14 6:48 PM
 */
public interface Data<D extends Data> {
    public void add(D dataToAdd);

    public void subtract(D dataToSubtract);

    public D copy();
}
