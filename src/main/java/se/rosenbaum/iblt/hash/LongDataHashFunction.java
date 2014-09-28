package se.rosenbaum.iblt.hash;

import se.rosenbaum.iblt.data.IntegerData;
import se.rosenbaum.iblt.data.LongData;

import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LongDataHashFunction implements HashFunction<LongData, IntegerData> {
    MessageDigest digest;

    public LongDataHashFunction() {
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Could not create MessageDigest SHA-256", e);
        }
    }

    @Override
    public IntegerData hash(LongData data) {
        digest.reset();

        byte[] longBytes = ByteBuffer.allocate(8).putLong(data.getValue()).array();
        byte[] digested = digest.digest(longBytes);
        int result = ByteBuffer.allocate(4).put(digested, 28, 4).getInt(0);

        return new IntegerData(result);
    }
}
