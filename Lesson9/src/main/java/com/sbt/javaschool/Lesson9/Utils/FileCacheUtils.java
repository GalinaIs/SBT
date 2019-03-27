package com.sbt.javaschool.Lesson9.Utils;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

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

    private void closeInputStream(InputStream ins) {
        if (ins != null) {
            try {
                ins.close();
            } catch (IOException ex) {
                //NOP
            }
        }
    }

    private Object readCache(File file) throws IOException, ClassNotFoundException {
        InputStream ins = null;
        try {
            ins = new FileInputStream(file);
            if (isZip) ins = new GZIPInputStream(ins);
            try (ObjectInputStream oin = new ObjectInputStream(ins)) {
                return oin.readObject();
            }
        } catch (IOException ex) {
            throw new IOException("Exception readCache file: " + file.getAbsoluteFile(), ex);
        } catch (ClassNotFoundException ex) {
            throw new ClassNotFoundException("Exception readCache file: " + file.getAbsoluteFile(), ex);
        } finally {
            closeInputStream(ins);
        }
    }

    @Override
    public Object checkCache() {
        File file = new File(fileName);
        if (file.exists()) {
            try {
                return readCache(file);
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

    private void closeOutputStream(OutputStream ous) {
        if (ous != null) {
            try {
                ous.flush();
            } catch (IOException ex) {
                //NOP
            }
            try {
                ous.close();
            } catch (IOException ex) {
                //NOP
            }
        }
    }

    private void writeCache(File file, Object result) throws IOException {
        OutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            if (isZip) fos = new GZIPOutputStream(fos);
            try (ObjectOutputStream oos = new ObjectOutputStream(fos)) {
                oos.writeObject(result);
            }
        } catch (NotSerializableException ex) {
            throw new NotSerializableException("Return type of the method must be serializable");
        } catch (IOException ex) {
            throw new IOException("Exception writeCache file: " + file.getAbsoluteFile(), ex);
        } finally {
            closeOutputStream(fos);
        }
    }

    @Override
    public void putInCache(Object result) {
        result = cutList(result);

        File file = new File(fileName);
        try {
            writeCache(file, result);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
