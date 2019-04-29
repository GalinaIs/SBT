package com.sbt.ex1.bean.watcher;

import com.sbt.ex1.bean.file.CopierFile;

import java.io.*;
import java.nio.file.*;
import java.util.Date;
import java.util.concurrent.*;

import static java.nio.file.StandardWatchEventKinds.*;

public class WatcherInDirectory extends WatcherAbstract {
    private File directoryIn;
    private File directoryOut;
    private final ExecutorService executor = Executors.newFixedThreadPool(1);

    public WatcherInDirectory(File directoryIn, File directoryOut) {
        super(directoryIn);
        this.directoryIn = directoryIn;
        this.directoryOut = directoryOut;
    }

    @Override
    protected void eventAfter(String filename, String typeModify, Date date) {
        replaceFile(filename);
    }

    @Override
    protected WatchEvent.Kind<?>[] watchingEventKind() {
        return new WatchEvent.Kind[]{ENTRY_CREATE, ENTRY_MODIFY};
    }

    @Override
    protected void delayInWork() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void replaceFile(String fileName) {
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }

        executor.submit(new CopierFile(directoryIn, directoryOut));
    }
}