package se.rosenbaum.iblt.hash;

import se.rosenbaum.iblt.data.ByteArrayData;
import se.rosenbaum.iblt.data.LongData;

import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class ByteArrayDataHashFunction implements HashFunction<ByteArrayData, ByteArrayData> {
    MessageDigest digest;
    int byteArraySize;

    public ByteArrayDataHashFunction(int byteArraySizeOfResult) {
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Could not create MessageDigest SHA-256", e);
        }
        this.byteArraySize = byteArraySizeOfResult;
    }

    @Override
    public ByteArrayData hash(ByteArrayData data) {
        digest.reset();

        byte[] digested = digest.digest(data.getValue());

        return new ByteArrayData(Arrays.copyOfRange(digested, 32 - byteArraySize, 32));
    }
}
