package se.rosenbaum.iblt.hash;

import se.rosenbaum.iblt.data.Data;

public interface HashFunction<D extends Data, H extends Data> {
    public H hash(D data);
}
