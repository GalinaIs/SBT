package com.sbt.javaschool.Lesson9.Utils;

import java.io.*;
import java.util.*;

public class MemoryCacheUtils implements CacheUtils {
    private static HashMap<String, byte[]> cacheMemory = new HashMap<>();
    private String identityName;
    private int listSize;

    public MemoryCacheUtils(String identityName, int listSize) {
        this.identityName = identityName;
        this.listSize = listSize;
    }

    private Object readCache(byte[] arrayByte) throws IOException, ClassNotFoundException {
        try (ByteArrayInputStream bain = new ByteArrayInputStream(arrayByte);
             ObjectInputStream oin = new ObjectInputStream(bain)) {
            return oin.readObject();
        } catch (IOException ex) {
            throw new IOException("Exception readCache from memory", ex);
        } catch (ClassNotFoundException ex) {
            throw new ClassNotFoundException("Exception readCache from memory", ex);
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

    private Object cutList(Object result) {
        if (!(result instanceof List)) return result;

        if (((List) result).size() >= listSize) {
            try {
                result = result.getClass().getConstructor(Collection.class).
                        newInstance(((List) result).subList(0, listSize));
            } catch (ReflectiveOperationException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    private void writeCache(Object result) throws IOException {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(result);
            cacheMemory.put(identityName, baos.toByteArray());
        } catch (NotSerializableException ex) {
            throw new NotSerializableException("Return type of the method must be serializable");
        } catch (IOException ex) {
            throw new IOException("Exception writeCache from memory", ex);
        }
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