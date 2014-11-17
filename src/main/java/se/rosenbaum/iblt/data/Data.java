package se.rosenbaum.iblt.data;

public interface Data<D extends Data> {
    public void add(D dataToAdd);

    public void subtract(D dataToSubtract);

    public boolean isZero();

    public D invertCopy();

    public D copy();
}
