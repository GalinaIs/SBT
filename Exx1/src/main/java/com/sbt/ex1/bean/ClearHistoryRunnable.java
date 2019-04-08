package com.sbt.ex1.bean;

import java.io.*;

public class ClearHistoryRunnable implements Runnable {
    private File fileHistory;

    public ClearHistoryRunnable(File fileHistory) {
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
