package com.sbt.javaschool.Lesson9.Utils;

import java.io.*;
import java.util.concurrent.*;

public class MemoryCacheUtils extends CacheAbstract implements CacheUtils {
    private static ConcurrentMap<String, byte[]> cacheMemory = new ConcurrentHashMap<>();

    public MemoryCacheUtils(String identityName, int listSize) {
        super(identityName, listSize);
    }

    private Object readCache(byte[] arrayByte) throws IOException, ClassNotFoundException {
        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(arrayByte)) {
            return readObject(byteArrayInputStream);
        }
    }

    private void writeCache(Object result) throws IOException {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            writeObject(byteArrayOutputStream, result);
            cacheMemory.put(identityName, byteArrayOutputStream.toByteArray());
        }
    }

    @Override
    public Object checkCache() {
        byte[] arrayByte = cacheMemory.get(identityName);
        if (arrayByte != null) {
            try {
                return readCache(arrayByte);
            } catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public void putInCache(Object result) {
        result = cutList(result);

        try {
            writeCache(result);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
}