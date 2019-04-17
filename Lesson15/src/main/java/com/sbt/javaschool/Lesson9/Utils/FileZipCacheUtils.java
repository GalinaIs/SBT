package com.sbt.javaschool.Lesson9.Utils;

import java.io.*;
import java.util.zip.*;

public class FileZipCacheUtils extends FileCacheAbstract {
    public FileZipCacheUtils(String identityName, int listSize, String filePath) {
        super(identityName, listSize, filePath);
    }

    protected Object readCache(File file) throws IOException, ClassNotFoundException {
        try (InputStream inputStream = new FileInputStream(file);
             GZIPInputStream gzipInputStream = new GZIPInputStream(inputStream)) {
            return readObject(gzipInputStream);
        }
    }

    protected String getFileName(String identityName) {
        return filePath + File.separator + identityName + ".zip";
    }

    protected void writeCache(File file, Object result) throws IOException {
        try (OutputStream outputStream = new FileOutputStream(file);
             GZIPOutputStream gzipOutputStream = new GZIPOutputStream(outputStream)) {
            writeObject(gzipOutputStream, result);
        }
    }
}
