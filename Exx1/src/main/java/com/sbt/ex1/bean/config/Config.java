package com.sbt.ex1.bean.config;

import java.io.*;
import java.util.concurrent.*;
import java.util.regex.*;

public class Config {
    private static final String FILE_CONFIG_PATH = "D:\\JavaSBTGit\\SBT\\Exx1\\src\\main\\resources\\config.txt";
    private static final File FILE_CONFIG = new File(FILE_CONFIG_PATH);
    private static final String NAME_PARAMETER_DIRECTORY_IN = "DirectoryIn";
    private static final String NAME_PARAMETER_DIRECTORY_OUT = "DirectoryOut";
    private static final String NAME_PARAMETER_FILE_HISTORY = "FileHistory";

    private File fileHistory;
    private File directoryIn;
    private File directoryOut;
    private static final ExecutorService executor = Executors.newCachedThreadPool();


    private static class ConfigHolder {
        private static final Config instance = init();

        private static Config init() {
            File fileHistory = null, directoryIn = null, directoryOut = null;
            Pattern patternFileHistory = Pattern.compile(NAME_PARAMETER_FILE_HISTORY + "\\s(.*)");
            Pattern patternDirectoryIn = Pattern.compile(NAME_PARAMETER_DIRECTORY_IN + "\\s(.*)");
            Pattern patternDirectoryOut = Pattern.compile(NAME_PARAMETER_DIRECTORY_OUT + "\\s(.*)");
            try (FileReader inStream = new FileReader(FILE_CONFIG);
                 BufferedReader bufferedReader = new BufferedReader(inStream)) {
                String line = bufferedReader.readLine();

                while (line != null) {
                    Matcher matcherFileHistory = patternFileHistory.matcher(line);
                    if (matcherFileHistory.find()) fileHistory = new File(matcherFileHistory.group(1));

                    Matcher matcherDirectoryIn = patternDirectoryIn.matcher(line);
                    if (matcherDirectoryIn.find()) directoryIn = new File(matcherDirectoryIn.group(1));

                    Matcher matcherDirectoryOut = patternDirectoryOut.matcher(line);
                    if (matcherDirectoryOut.find()) directoryOut = new File(matcherDirectoryOut.group(1));

                    line = bufferedReader.readLine();
                }

                if (fileHistory == null || directoryIn == null || directoryOut == null)
                    throw new IOException();
            } catch (IOException ex) {
                throw new RuntimeException("Exception with try reading data from file " + FILE_CONFIG, ex);
            }
            return new Config(fileHistory, directoryIn, directoryOut);
        }
    }

    private Config(File fileHistory, File directoryIn, File directoryOut) {
        this.fileHistory = fileHistory;
        this.directoryIn = directoryIn;
        this.directoryOut = directoryOut;
    }

    public static Config getInstance() {
        return ConfigHolder.instance;
    }

    public File getFileHistory() {
        return fileHistory;
    }

    public File getDirectoryIn() {
        return directoryIn;
    }

    public void setDirectoryIn(File directoryIn) {
        this.directoryIn = directoryIn;

        executor.submit(new UpdateConfig(FILE_CONFIG, NAME_PARAMETER_DIRECTORY_IN, directoryIn.getAbsolutePath()));
    }

    public File getDirectoryOut() {
        return directoryOut;
    }

    public void setDirectoryOut(File directoryOut) {
        this.directoryOut = directoryOut;

        executor.submit(new UpdateConfig(FILE_CONFIG, NAME_PARAMETER_DIRECTORY_OUT, directoryOut.getAbsolutePath()));
    }
}
