package com.sbt.ex1.bean;

import java.awt.*;
import java.io.*;
import java.util.Date;

public class UpdateHistoryRunnable implements Runnable {
    private File fileMonitor;
    private Date date;
    private String filename;
    private String typeModify;
    private TextArea textAreaHistory;

    public UpdateHistoryRunnable(File fileMonitor, String filename, String typeModify,
                                 Date date, TextArea textAreaHistory) {
        this.fileMonitor = fileMonitor;
        this.date = date;
        this.filename = filename;
        this.typeModify = typeModify;
        this.textAreaHistory = textAreaHistory;
    }

    @Override
    public void run() {
        String dateChangeInfo = "Date change: " + date.toString() + "\n";
        String fileChangeInfo = "File change: " + filename + "\n";
        String typeChangeInfo = "Type change: " + typeModify + "\n";
        String separator = "-----------------------------------------------------------------\n";
        try {
            writeFile(dateChangeInfo, fileChangeInfo, typeChangeInfo, separator);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        EventQueue.invokeLater(() -> {
            String textAreaInfo = textAreaHistory.getText();
            textAreaInfo += dateChangeInfo;
            textAreaInfo += fileChangeInfo;
            textAreaInfo += typeChangeInfo;
            textAreaInfo += separator;
            textAreaHistory.setText(textAreaInfo);
        });
    }

    private void writeFile(String dateChangeInfo, String fileChangeInfo, String typeChangeInfo, String separator)
            throws IOException {
        try (FileWriter outStream = new FileWriter(fileMonitor, true)) {
            outStream.write(dateChangeInfo);
            outStream.write(fileChangeInfo);
            outStream.write(typeChangeInfo);
            outStream.write(separator);
        } catch (IOException ex) {
            throw new IOException("Error with try write data in file " + fileMonitor, ex);
        }
    }
}
