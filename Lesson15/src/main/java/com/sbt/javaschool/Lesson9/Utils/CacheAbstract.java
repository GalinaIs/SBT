package com.sbt.javaschool.Lesson9.Utils;

import com.sun.javaws.CacheUtil;

import java.io.*;
import java.util.*;

abstract class CacheAbstract implements CacheUtils {
    String identityName;
    private int listSize;

    CacheAbstract(String identityName, int listSize) {
        this.identityName = identityName;
        this.listSize = listSize;
    }

    Object readObject(InputStream inputStream) throws IOException, ClassNotFoundException {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(inputStream)) {
            return objectInputStream.readObject();
        } catch (IOException ex) {
            throw new IOException("Exception readCache from memory", ex);
        } catch (ClassNotFoundException ex) {
            throw new ClassNotFoundException("Exception readCache from memory", ex);
        }
    }

    Object cutList(Object result) {
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

    void writeObject(OutputStream outputStream, Object object) throws IOException {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream)) {
            objectOutputStream.writeObject(object);
        } catch (NotSerializableException ex) {
            throw new NotSerializableException("Return type of the method must be serializable");
        } catch (IOException ex) {
            throw new IOException("Exception writeObject in: " + outputStream, ex);
        }
    }
}