package se.rosenbaum.iblt.hash;

import se.rosenbaum.iblt.data.IntegerData;
import se.rosenbaum.iblt.data.LongData;

import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LongDataHashFunction implements HashFunction<LongData, LongData> {
    MessageDigest digest;

    public LongDataHashFunction() {
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Could not create MessageDigest SHA-256", e);
        }
    }

    @Override
    public LongData hash(LongData data) {
        digest.reset();

        byte[] longBytes = ByteBuffer.allocate(8).putLong(data.getValue()).array();
        byte[] digested = digest.digest(longBytes);
        long result = ByteBuffer.allocate(8).put(digested, 24, 4).getLong(0);

        return new LongData(result);
    }
}
