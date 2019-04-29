package com.sbt.ex1.bean.history;

import java.io.*;

public class ClearHistory implements Runnable {
    private File fileHistory;

    public ClearHistory(File fileHistory) {
        this.fileHistory = fileHistory;
    }

    @Override
    public void run() {
        try (FileWriter outStream = new FileWriter(fileHistory, false)) { }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}