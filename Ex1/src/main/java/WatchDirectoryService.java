import java.io.*;
import java.nio.file.*;
import java.util.Date;

import static java.nio.file.StandardWatchEventKinds.*;

public class WatchDirectoryService implements Runnable {
    private final String directory;
    private static final File fileMonitor =
            new File("D:\\JavaSBTGit\\SBT\\Ex1\\src\\main\\resources\\changeDirectory.txt");

    public WatchDirectoryService(String directory) {
        this.directory = directory;
    }

    @Override
    public void run() {
        try {
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

        Path dir = Paths.get(directory);
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

        WriteFileRunnable writeFileRunnable = new WriteFileRunnable(fileMonitor, filename, typeModify, date);
        Thread thread = new Thread(writeFileRunnable);
        thread.start();
    }
}
