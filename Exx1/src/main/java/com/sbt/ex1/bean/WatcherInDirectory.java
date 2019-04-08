package com.sbt.ex1.bean;

import java.io.*;
import java.nio.file.*;
import java.util.concurrent.TimeUnit;

import static java.nio.file.StandardWatchEventKinds.*;

public class WatcherInDirectory implements Runnable {
    private File directoryIn;
    private File directoryOut;

    public WatcherInDirectory(File directoryIn, File directoryOut) {
        this.directoryIn = directoryIn;
        this.directoryOut = directoryOut;
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

        Path dir = Paths.get(directoryIn.getAbsolutePath());
        WatchKey key;
        try {
            key = dir.register(watcher, ENTRY_CREATE, ENTRY_MODIFY);
        } catch (IOException ex) {
            throw new IOException("Error with try register WatchEvent", ex);
        }

        while (true) {
            try {
                key = watcher.take();
            } catch (InterruptedException ex) {
                return;
            }

            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            for (WatchEvent<?> event : key.pollEvents()) {
                System.out.println(event.context().toString());
                replaceFile(event.context().toString());
            }
            boolean valid = key.reset();
            if (!valid) {
                break;
            }
        }
    }

    private void replaceFile(String fileName) {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        SendFileRunnable sendFileRunnable = new SendFileRunnable(
                new File(directoryIn + File.separator + fileName),
                new File(directoryOut + File.separator + fileName));
        new Thread(sendFileRunnable).start();
    }


}
