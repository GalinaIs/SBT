package com.sbt.cachable.serialize;

import java.io.*;

public class SerializeUtil {
    public static Object arrayBytesToObject(byte[] arrayByte) throws IOException, ClassNotFoundException {
        try (ByteArrayInputStream bain = new ByteArrayInputStream(arrayByte);
             ObjectInputStream oin = new ObjectInputStream(bain)) {
            return oin.readObject();
        }
    }

    public static byte[] objectToArrayByte(Object result) throws IOException {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(result);
            return baos.toByteArray();
        } catch (NotSerializableException ex) {
            throw new NotSerializableException("Return type of the method must be serializable");
        }
    }
}
