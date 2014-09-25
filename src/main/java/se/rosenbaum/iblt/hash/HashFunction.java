package se.rosenbaum.iblt.hash;

import se.rosenbaum.iblt.data.Data;

/**
 * Created with IntelliJ IDEA.
 * User: kalle
 * Date: 9/22/14
 * Time: 10:06 PM
 * To change this template use File | Settings | File Templates.
 */
public interface HashFunction<D extends Data, H extends Data> {
    public H hash(D data);
}
