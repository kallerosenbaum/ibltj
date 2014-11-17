package se.rosenbaum.iblt.hash;

import com.google.common.hash.Hashing;
import se.rosenbaum.iblt.data.IntegerData;

public class IntegerDataHashFunction implements HashFunction<IntegerData, IntegerData> {
    private final com.google.common.hash.HashFunction hashImplementation;

    public IntegerDataHashFunction() {
        hashImplementation = Hashing.murmur3_32();
    }

    @Override
    public IntegerData hash(IntegerData data) {
        return new IntegerData(hashImplementation.hashInt(data.getValue()).asInt());
    }
}
