package com.sbt.ex1.bean;

import java.awt.*;
import java.io.*;
import java.nio.file.*;
import java.util.Date;

import static java.nio.file.StandardWatchEventKinds.*;

public class WatchOutDirectory implements Runnable {
    private File monitoredDirectory;
    private File fileHistory;
    private TextArea textAreaHistory;

    public WatchOutDirectory(File monitoredDirectory, File fileHistory, TextArea textAreaHistory) {
        this.monitoredDirectory = monitoredDirectory;
        this.fileHistory = fileHistory;
        this.textAreaHistory = textAreaHistory;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted())
                runWatcher();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void runWatcher() throws IOException {
        WatchService watcher;
        try {
            watcher = FileSystems.getDefault().newWatchService();
        } catch (IOException ex) {
            throw new IOException("Error with try create  WatchService", ex);
        }

        Path dir = Paths.get(monitoredDirectory.getAbsolutePath());
        WatchKey key;
        try {
            key = dir.register(watcher, ENTRY_CREATE, ENTRY_MODIFY, ENTRY_DELETE);
        } catch (IOException ex) {
            throw new IOException("Error with try register WatchEvent", ex);
        }

        while (true) {
            try {
                key = watcher.take();
            } catch (InterruptedException ex) {
                return;
            }
            for (WatchEvent<?> event : key.pollEvents()) {
                String typeChange = event.kind().name().substring(6);
                checkModify(event.context().toString(), typeChange, new Date());
            }
            boolean valid = key.reset();
            if (!valid) {
                break;
            }
        }
    }

    private static String getFileExtension(String fileName) {
        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
            return fileName.substring(fileName.lastIndexOf(".")+1).toLowerCase();
        else return "";
    }

    private void checkModify(String filename, String typeModify, Date date) {
        if (getFileExtension(filename).equals("tmp")) return;

        new Thread(new UpdateHistoryRunnable(fileHistory, filename, typeModify, date, textAreaHistory)).start();
    }
}
