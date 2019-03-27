package com.sbt.javaschool.Lesson9.Utils;

import java.io.*;
import java.util.*;
import java.util.zip.*;

public class FileCacheUtils implements CacheUtils {
    private boolean isZip;
    private int listSize;
    private static String filePath;
    private String fileName;

    public FileCacheUtils(String identityName, int listSize, boolean isZip) {
        this.isZip = isZip;
        this.listSize = listSize;
        fileName = filePath + File.separator + identityName;
        fileName += isZip ? ".zip" : ".txt";
    }

    public static void setFilePath(String filePath) {
        FileCacheUtils.filePath = filePath;
    }

    @Override
    public Object checkCache() {
        File file = new File(fileName);
        if (file.exists()) {
            try {
                InputStream ins = new FileInputStream(file);
                if (isZip) ins = new GZIPInputStream(ins);
                try (ObjectInputStream oin = new ObjectInputStream(ins)) {
                    return oin.readObject();
                }
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

    @Override
    public void putInCache(Object result) throws NotSerializableException {
        result = cutList(result);

        File file = new File(fileName);
        try {
            OutputStream fos = new FileOutputStream(file);
            if (isZip) fos = new GZIPOutputStream(fos);
            try (ObjectOutputStream oos = new ObjectOutputStream(fos)) {
                oos.writeObject(result);
            }
        } catch (NotSerializableException ex) {
            throw new NotSerializableException("Return type of the method must be serializable");
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
