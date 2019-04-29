package com.sbt.ex1.bean.watcher;

import com.sbt.ex1.bean.history.RecordHistory;
import com.sbt.ex1.bean.history.UpdateHistory;

import java.awt.*;
import java.io.*;
import java.nio.file.*;
import java.util.Date;
import java.util.concurrent.*;

import static java.nio.file.StandardWatchEventKinds.*;

public class WatchOutDirectory extends WatcherAbstract {
    private final File fileHistory;
    private final TextArea textAreaHistory;
    private final ExecutorService executor = Executors.newFixedThreadPool(1);

    public WatchOutDirectory(File monitoredDirectory, File fileHistory, TextArea textAreaHistory) {
        super(monitoredDirectory);
        System.out.println(monitoredDirectory);
        this.fileHistory = fileHistory;
        this.textAreaHistory = textAreaHistory;
    }

    @Override
    protected void eventAfter(String filename, String typeModify, Date date) {
        if (getFileExtension(filename).equals("tmp")) return;

        RecordHistory recordHistory = new RecordHistory(filename, typeModify, date);
        executor.submit(new UpdateHistory(fileHistory, recordHistory, textAreaHistory));
    }

    @Override
    protected WatchEvent.Kind<?>[] watchingEventKind() {
        return new WatchEvent.Kind[]{ENTRY_CREATE, ENTRY_MODIFY, ENTRY_DELETE};
    }

    private static String getFileExtension(String fileName) {
        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
            return fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        return "";
    }
}
