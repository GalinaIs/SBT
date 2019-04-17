package com.sbt.javaschool.Lesson9.Utils;

import java.io.*;

public class FileCacheUtils extends FileCacheAbstract {
    public FileCacheUtils(String identityName, int listSize, String filePath) {
        super(identityName, listSize, filePath);
    }

    protected Object readCache(File file) throws IOException, ClassNotFoundException {
        try (InputStream inputStream = new FileInputStream(file)) {
            return readObject(inputStream);
        }
    }

    protected String getFileName(String identityName) {
        return filePath + File.separator + identityName + ".txt";
    }

    protected void writeCache(File file, Object result) throws IOException {
        try (OutputStream outputStream = new FileOutputStream(file)) {
            writeObject(outputStream, result);
        }
    }
}
