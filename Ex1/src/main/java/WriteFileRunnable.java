import java.io.*;
import java.util.Date;

public class WriteFileRunnable implements Runnable {
    private File fileMonitor;
    private Date date;
    private String filename;
    private String typeModify;

    public WriteFileRunnable(File fileMonitor, String filename, String typeModify, Date date) {
        this.fileMonitor = fileMonitor;
        this.date = date;
        this.filename = filename;
        this.typeModify = typeModify;
    }

    @Override
    public void run() {
        try {
            writeFile();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void writeFile() throws IOException {
        try (FileWriter outStream = new FileWriter(fileMonitor, true)) {
            outStream.write("Date change: " + date.toString() + "\n");
            outStream.write("File change: " + filename + "\n");
            outStream.write("Type change: " + typeModify + "\n");
            outStream.write("------------------------------------------\n");
        } catch (IOException ex) {
            throw new IOException("Error with try write data in file " + fileMonitor, ex);
        }
    }
}
