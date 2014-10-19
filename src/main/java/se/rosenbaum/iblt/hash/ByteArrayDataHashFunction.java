package se.rosenbaum.iblt.hash;

import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import se.rosenbaum.iblt.data.ByteArrayData;
import se.rosenbaum.iblt.data.LongData;

import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class ByteArrayDataHashFunction implements HashFunction<ByteArrayData, ByteArrayData> {
    private final com.google.common.hash.HashFunction hashImplementation;
    int byteArraySize;

    public ByteArrayDataHashFunction(int byteArraySizeOfResult) {
        if (byteArraySizeOfResult <= 4) {
            hashImplementation = Hashing.murmur3_32();
        } else {
            hashImplementation = Hashing.murmur3_128();
        }
        this.byteArraySize = byteArraySizeOfResult;
    }

    @Override
    public ByteArrayData hash(ByteArrayData data) {
        HashCode hashCode = hashImplementation.hashBytes(data.getValue());
        return new ByteArrayData(Arrays.copyOfRange(hashCode.asBytes(), 0, byteArraySize));
    }
}
