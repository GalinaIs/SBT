package com.sbt.javaschool.Lesson9.Utils;

import java.io.*;

abstract class FileCacheAbstract extends CacheAbstract implements CacheUtils {
    String filePath;

    FileCacheAbstract(String identityName, int listSize, String filePath) {
        super(identityName, listSize);
        this.filePath = filePath;
    }

    abstract protected Object readCache(File file) throws IOException, ClassNotFoundException;

    abstract protected String getFileName(String identityName);

    @Override
    public Object checkCache() {
        File file = new File(getFileName(identityName));
        if (file.exists()) {
            try {
                return readCache(file);
            } catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

    abstract protected void writeCache(File file, Object result) throws IOException;

    @Override
    public void putInCache(Object result) {
        result = cutList(result);

        try {
            writeCache(new File(getFileName(identityName)), result);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
