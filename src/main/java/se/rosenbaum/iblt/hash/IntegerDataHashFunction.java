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
        Integer integerToDigest = new Integer(data.getValue());

        byte[] intBytes = ByteBuffer.allocate(4).putInt(integerToDigest).array();

        byte[] digested = digest.digest(intBytes);

        int result = 0;
        for (int i = 28; i < 32; i++) {
            result += ((int)digested[i]) << 8*i;
        }

        return new IntegerData(result);
    }
}
