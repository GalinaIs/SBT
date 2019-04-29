package com.sbt.ex1.bean.config;

import java.io.*;
import java.util.*;

public class UpdateConfig implements Runnable {
    private final File fileConfig;
    private final String nameParameterInFile;
    private final String newValueParameter;

    public UpdateConfig(File fileConfig, String nameParameterInFile, String newValueParameter) {
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

    private void readFile(List<String> lineFileList) throws IOException {
        try (FileReader inStream = new FileReader(fileConfig);
             BufferedReader bufferedReader = new BufferedReader(inStream)) {
            String line = bufferedReader.readLine();
            while (line != null) {
                lineFileList.add(line);
                line = bufferedReader.readLine();
            }
        } catch (IOException ex) {
            throw new IOException("Exception with reading data from file " + fileConfig, ex);
        }
    }

    private void writeFile(List<String> lineFileList) throws IOException {
        try (FileWriter outStream = new FileWriter(fileConfig, false)) {
            for (String lineFile : lineFileList) {
                outStream.write(lineFile + "\n");
            }
        } catch (IOException ex) {
            throw new IOException("Exception with writing data in file " + fileConfig, ex);
        }
    }

    private void updateListParameters(List<String> listParameters) {
        for (String parameter : listParameters) {
            if (parameter.startsWith(nameParameterInFile)) {
                listParameters.remove(parameter);
                break;
            }
        }

        listParameters.add(nameParameterInFile + " " + newValueParameter);
    }

    private void changeFile() throws IOException {
        List<String> listParameters = new ArrayList<>();
        readFile(listParameters);

        updateListParameters(listParameters);

        writeFile(listParameters);
    }
}