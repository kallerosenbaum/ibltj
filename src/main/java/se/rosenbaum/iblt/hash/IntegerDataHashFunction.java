package se.rosenbaum.iblt.hash;

import se.rosenbaum.iblt.data.IntegerData;

import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class IntegerDataHashFunction implements HashFunction<IntegerData, IntegerData> {
    MessageDigest digest;
    public IntegerDataHashFunction() {
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Could not create MessageDigest SHA-256", e);
        }
    }

    @Override
    public IntegerData hash(IntegerData data) {
        digest.reset();

        byte[] intBytes = ByteBuffer.allocate(4).putInt(data.getValue()).array();
        byte[] digested = digest.digest(intBytes);
        int result = ByteBuffer.allocate(4).put(digested, 28, 4).getInt(0);

        return new IntegerData(result);
    }
}
