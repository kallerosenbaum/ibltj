package se.rosenbaum.iblt.hash;

import com.google.common.hash.Hashing;
import se.rosenbaum.iblt.data.LongData;

import java.security.MessageDigest;

public class LongDataHashFunction implements HashFunction<LongData, LongData> {
    MessageDigest digest;
    private final com.google.common.hash.HashFunction hashImplementation;


    public LongDataHashFunction() {
        hashImplementation = Hashing.murmur3_128();
    }

    @Override
    public LongData hash(LongData data) {
        return new LongData(hashImplementation.hashLong(data.getValue()).asLong());
    }
}
