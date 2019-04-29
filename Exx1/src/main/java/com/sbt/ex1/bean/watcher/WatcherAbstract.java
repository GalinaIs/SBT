package com.sbt.ex1.bean.watcher;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.Date;

abstract class WatcherAbstract implements Runnable {
    private final File monitoredDirectory;

    protected WatcherAbstract(File monitoredDirectory) {
        this.monitoredDirectory = monitoredDirectory;
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
            throw new IOException("Exception with creating  WatchService", ex);
        }

        Path dir = Paths.get(monitoredDirectory.getAbsolutePath());
        WatchKey key;
        try {
            key = dir.register(watcher, watchingEventKind());
        } catch (IOException ex) {
            throw new IOException("Exception with register WatchEvent", ex);
        }

        while (true) {
            try {
                key = watcher.take();
            } catch (InterruptedException ex) {
                return;
            }

            delayInWork();

            for (WatchEvent<?> event : key.pollEvents()) {
                String typeChange = event.kind().name().substring(6);
                eventAfter(event.context().toString(), typeChange, new Date());
            }
            boolean valid = key.reset();
            if (!valid) {
                break;
            }
        }
    }

    protected abstract void eventAfter(String filename, String typeModify, Date date);

    protected void delayInWork() {}

     protected abstract WatchEvent.Kind<?>[] watchingEventKind();
}
