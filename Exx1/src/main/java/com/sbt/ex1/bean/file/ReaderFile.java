package com.sbt.ex1.bean.file;

import java.io.*;

public class ReaderFile {
    public static String readFile(File file) throws IOException {
        StringBuilder result = new StringBuilder();
        try (FileReader inStream = new FileReader(file);
             BufferedReader bufferedReader = new BufferedReader(inStream)) {
            String line = bufferedReader.readLine();
            while (line != null) {
                result.append(line).append("\n");
                line = bufferedReader.readLine();
            }
        } catch (IOException ex) {
            throw new IOException("Error with try read data from file " + file, ex);
        }
        return result.toString();
    }
}
