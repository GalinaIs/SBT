package com.sbt.ex1.bean;

import java.io.*;
import java.util.*;

public class UpdateConfigRunnable implements Runnable {
    private final File fileConfig;
    private final String nameParameterInFile;
    private final String newValueParameter;

    public UpdateConfigRunnable(File fileConfig, String nameParameterInFile, String newValueParameter) {
        this.fileConfig = fileConfig;
        this.nameParameterInFile = nameParameterInFile;
        this.newValueParameter = newValueParameter;
    }

    @Override
    public void run() {
        try {
            changeFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readFile(List<String> lineFileList) throws IOException{
        try (FileReader inStream = new FileReader(fileConfig);
             BufferedReader bufferedReader = new BufferedReader(inStream)) {
            String line = bufferedReader.readLine();
            while (line != null) {
                lineFileList.add(line);
                line = bufferedReader.readLine();
            }
        } catch (IOException ex) {
            throw new IOException("Error with try read data from file " + fileConfig, ex);
        }
    }

    private void writeFile(List<String> lineFileList) throws IOException {
        try (FileWriter outStream = new FileWriter(fileConfig, false)) {
            for (String lineFile : lineFileList) {
                outStream.write(lineFile + "\n");
            }
        } catch (IOException ex) {
            throw new IOException("Error with try write data in file " + fileConfig, ex);
        }
    }

    private void changeFile() throws IOException{
        List<String> lineFileList = new ArrayList<>();
        readFile(lineFileList);

        for (String lineFile : lineFileList) {
            if (lineFile.startsWith(nameParameterInFile)) {
                lineFileList.remove(lineFile);
                break;
            }
        }

        lineFileList.add(nameParameterInFile + " " + newValueParameter);

        writeFile(lineFileList);
    }
}